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

import java.math.BigDecimal;
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
    public ResponseEntity<String> buyItem(@PathVariable("item") Long itemId, @RequestParam("token") String token) throws ParseException, JOSEException {

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

        //item.setStatus(Dictionary.Status.SOLD);
        //itemRepository.aggiornaStato(item.getId(), Dictionary.Status.SOLD.toString());
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
        return ResponseEntity.ok("oggetto venduto");
    }


    @PostMapping("/addFavorites/{item}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> addFavorites(@PathVariable("item") Long itemId, @RequestParam("token") String token) throws ParseException, JOSEException {

        var username = TokenStore.getInstance().getUser(token);
        User user = userRepository.findByUsername(username);
        Item item = itemRepository.findById(itemId).orElse(null);


        Favorites favorites = new Favorites();
        favorites.setIdUtente(user.getId());
        favorites.setIdOggetto(item.getId());

        favoritesDao.save(favorites);


        return ResponseEntity.ok("aggiunto ai preferiti");
    }


}
