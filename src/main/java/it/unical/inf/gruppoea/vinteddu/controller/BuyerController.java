package it.unical.inf.gruppoea.vinteddu.controller;


import com.nimbusds.jose.JOSEException;
import it.unical.inf.gruppoea.vinteddu.data.dao.*;
import it.unical.inf.gruppoea.vinteddu.data.entities.*;
import it.unical.inf.gruppoea.vinteddu.dto.Dictionary.Dictionary;
import it.unical.inf.gruppoea.vinteddu.security.TokenStore;
import it.unical.inf.gruppoea.vinteddu.utilities.EmailManager;
import it.unical.inf.gruppoea.vinteddu.utilities.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class BuyerController {


    @Autowired
    private ItemDao itemRepository;
    @Autowired
    private PurchaseDao purchaseRepository;
    @Autowired
    private UserDao userRepository;
    @Autowired
    private WalletDao walletRepository;
    @Autowired
    private FavoritesDao favoritesDao;

    @Autowired
    private EmailManager emailManager;

    @PostMapping("/buyItem/{item}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public HttpStatus buyItem(@PathVariable("item") Long itemId, @RequestParam("token") String token) throws ParseException, JOSEException {

        try{
            var username = TokenStore.getInstance().getUser(token);
            User user = userRepository.findByUsername(username);
            Item item = itemRepository.findById(itemId).orElse(null);

            Date currentDate = new Date();

            Purchase purchase = new Purchase();
            purchase.setBuyer(user);
            purchase.setId(IdGenerator.generateId());
            purchase.setItem(item);
            purchase.setSeller(item.getSeller());
            purchase.setPrice(Math.toIntExact(item.getPrice()));
            purchase.setPurchaseDate(currentDate);

            item.setStatus(Dictionary.Status.SOLD);
            purchaseRepository.save(purchase);

            Optional<Wallet> wallet = walletRepository.findById(user.getId());
            Optional<Wallet> wallet_riceve = walletRepository.findById(item.getSeller().getId());
            Wallet wallet_ = wallet.orElse(null);
            Wallet wallet_riceve_ = wallet_riceve.orElse(null);
            var saldo = wallet_.getSaldo() - item.getPrice() ;
            var saldo_riceve = wallet_riceve_.getSaldo() + item.getPrice();
            wallet_.setSaldo((int) saldo);
            walletRepository.aggiornaSaldo(wallet_.getId_utente(), (int) saldo);
            walletRepository.aggiornaSaldo(wallet_riceve_.getId_utente(), (int) saldo_riceve);


            //itemRepository.aggiornaStato(item.getId(), Dictionary.Status.IN_DELIVERY);

            String param = item.getName()+ " $" +item.getPrice().toString()+" giorno: "+ currentDate.toString();
            emailManager.sendEmail(3, param ,user.getEmail());
            return HttpStatus.OK;
        }catch(Exception e){
            return HttpStatus.BAD_REQUEST;
        }

    }


    @PostMapping("/addFavorites/{item}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> addFavorites(@PathVariable("item") Long itemId, @RequestParam("token") String token) throws ParseException, JOSEException {

        var username = TokenStore.getInstance().getUser(token);
        User user = userRepository.findByUsername(username);
        Item item = itemRepository.findById(itemId).orElse(null);


        Favorites favorites = new Favorites();
        favorites.setId(IdGenerator.generateIdFavorites());
        favorites.setId_Utente(user.getId());
        favorites.setId_Oggetto(item.getId());

        Favorites favoritesSaved = favoritesDao.save(favorites);
        if (favoritesSaved != null) {
            return ResponseEntity.ok("aggiunto ai preferiti");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/SaldoOk/{itemId}/{token}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public HttpStatus getSaldoOK(@PathVariable("token") String token , @PathVariable("itemId") Long itemId) throws ParseException, JOSEException {

        var username = TokenStore.getInstance().getUser(token);
        User utente = userRepository.findByUsername(username);
        Item item = itemRepository.findById(itemId).orElse(null);

        Wallet wallet = walletRepository.findById(utente.getId()).orElse(null);

        if(wallet.getSaldo()>item.getPrice()){
            return HttpStatus.OK;
        }


        return HttpStatus.BAD_REQUEST;
    }



}
