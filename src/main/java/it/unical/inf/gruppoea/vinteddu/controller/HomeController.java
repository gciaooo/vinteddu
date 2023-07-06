package it.unical.inf.gruppoea.vinteddu.controller;
import com.nimbusds.jose.JOSEException;
import it.unical.inf.gruppoea.vinteddu.data.dao.ItemDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.PurchaseDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.UserDao;
import it.unical.inf.gruppoea.vinteddu.data.entities.Item;
import it.unical.inf.gruppoea.vinteddu.data.entities.Purchase;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import it.unical.inf.gruppoea.vinteddu.dto.Dictionary.Dictionary;
import it.unical.inf.gruppoea.vinteddu.dto.OggettoDTO;
import it.unical.inf.gruppoea.vinteddu.security.TokenStore;
import it.unical.inf.gruppoea.vinteddu.utilities.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v3", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class HomeController {



    @Autowired
    private ItemDao itemRepository;
    @Autowired
    private PurchaseDao purchaseRepository;
    @Autowired
    private UserDao userRepository;


    @GetMapping("/search/{nome}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<OggettoDTO>> searchItemByName(@PathVariable("nome") String nome, @RequestParam String token) throws ParseException, JOSEException {
        List<Item> oggetti = itemRepository.findByNameContainingIgnoreCase(nome);
        List<OggettoDTO> lista = new ArrayList<>();
        var username = TokenStore.getInstance().getUser(token);
        User utente = userRepository.findByUsername(username);


        for(int i = 0; i<oggetti.size(); i++){

            var oggetto = new OggettoDTO();
            oggetto.setId(Math.toIntExact(oggetti.get(i).getId()));
            oggetto.setNome(oggetti.get(i).getName());
            oggetto.setPrezzo(BigDecimal.valueOf(oggetti.get(i).getPrice()));
            if(oggetti.get(i).getStatus()== Dictionary.Status.ON_SALE && oggetti.get(i).getSeller().getId()!=utente.getId()){
                lista.add(oggetto);
            }

        }
        return ResponseEntity.ok(lista);
    }

    @PostMapping("/add/{token}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<String> addOggetto(
            @PathVariable String token,
            @RequestParam("nome") String nome,
            @RequestParam("descrizione") String descrizione,
            @RequestParam("prezzo") Integer prezzo,
            @RequestParam("immagine") String immagine) throws ParseException, JOSEException {
        Item oggetto = new Item();
        var username = TokenStore.getInstance().getUser(token);
        User utente = userRepository.findByUsername(username);




        oggetto.setId(IdGenerator.generateIdItem());
        oggetto.setName(nome);
        oggetto.setDescription(descrizione);
        oggetto.setPrice(prezzo);
        oggetto.setCreationDate(LocalDate.now());
        oggetto.setStatus(Dictionary.Status.ON_SALE);
        oggetto.setSeller(utente);
        oggetto.setMainImage(immagine);

        Item savedOggetto = itemRepository.save(oggetto);

        if (savedOggetto != null) {
            return ResponseEntity.ok("Oggetto aggiunto con successo");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{token}/purchases")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<List<Purchase>> getUserPurchases(@PathVariable("token") String token) throws ParseException, JOSEException {
        var username = TokenStore.getInstance().getUser(token);
        User utente = userRepository.findByUsername(username);
        List<Purchase> userPurchases = purchaseRepository.findByBuyerId(utente.getId());
        return ResponseEntity.ok(userPurchases);
    }

    @GetMapping("/item/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<OggettoDTO> getItem(@PathVariable("itemId") Long itemId){
        Optional<Item> item = itemRepository.findById(itemId);
        Item item_value = item.orElse(null);
        var item_dto = new OggettoDTO();
        item_dto.setId(Math.toIntExact(item_value.getId()));
        item_dto.setIdUtente(Math.toIntExact(item_value.getSeller().getId()));
        item_dto.setNome(item_value.getName());
        item_dto.setDescrizione(item_value.getDescription());
        item_dto.setPrezzo(BigDecimal.valueOf(item_value.getPrice()));
        item_dto.setDataCreazione(item_value.getCreationDate());
        item_dto.setStato(item_value.getStatus());
        item_dto.setImmagini(item_value.getMainImage());

        return ResponseEntity.ok(item_dto);
    }





}
