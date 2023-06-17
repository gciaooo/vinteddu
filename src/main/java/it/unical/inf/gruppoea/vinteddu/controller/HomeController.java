package it.unical.inf.gruppoea.vinteddu.controller;
import it.unical.inf.gruppoea.vinteddu.data.dao.ItemDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.PurchaseDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.UserDao;
import it.unical.inf.gruppoea.vinteddu.data.entities.Item;
import it.unical.inf.gruppoea.vinteddu.data.entities.Purchase;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import it.unical.inf.gruppoea.vinteddu.dto.OggettoDTO;
import it.unical.inf.gruppoea.vinteddu.dto.UtenteDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

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


    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItemByName(@RequestParam("nome") String nome) {
        List<Item> oggetti = itemRepository.findByNameContainingIgnoreCase(nome);
        return ResponseEntity.ok(oggetti);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOggetto(@RequestBody OggettoDTO oggettoDTO, @RequestBody UtenteDTO utenteDTO){
        Item oggetto = new Item();
        User utente = new User();

        utente.setId((long) utenteDTO.getId());


        oggetto.setId((long) oggettoDTO.getId());
        oggetto.setName(oggettoDTO.getNome());
        oggetto.setDescription(oggettoDTO.getDescrizione());
        oggetto.setPrice(oggettoDTO.getPrezzo());
        oggetto.setCreationDate(oggettoDTO.getDataCreazione());
        oggetto.setStatus(oggettoDTO.getStato());
        oggetto.setSeller(utente);


        Item savedOggetto = itemRepository.save(oggetto);

        if (savedOggetto != null) {
            return ResponseEntity.ok("Oggetto aggiunto con successo");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{userId}/purchases")
    public ResponseEntity<List<Purchase>> getUserPurchases(@PathVariable("userId") Long userId) {
        List<Purchase> userPurchases = purchaseRepository.findByBuyerId(userId);
        return ResponseEntity.ok(userPurchases);
    }

    @GetMapping("/item/{itemId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<Item> getItem(@PathVariable("itemId") Long itemId){
        Optional<Item> item = itemRepository.findById(itemId);
        Item item_value = item.orElse(null);
        return ResponseEntity.ok(item_value);
    }





}
