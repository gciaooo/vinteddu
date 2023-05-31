package it.unical.inf.gruppoea.vinteddu.controller;


import it.unical.inf.gruppoea.vinteddu.data.dao.ItemDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.PurchaseDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.UserDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.WalletDao;
import it.unical.inf.gruppoea.vinteddu.data.entities.Item;
import it.unical.inf.gruppoea.vinteddu.data.entities.Purchase;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import it.unical.inf.gruppoea.vinteddu.data.entities.Wallet;
import it.unical.inf.gruppoea.vinteddu.dto.Dictionary.Dictionary;
import it.unical.inf.gruppoea.vinteddu.utilities.IdGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1/users", produces = "application/json")
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
    
    @PostMapping("/buyItem")
    public ResponseEntity<String> buyItem(@RequestParam double amount, @RequestBody Item item, @RequestParam User user){

        Date currentDate = new Date();

        Purchase purchase = new Purchase();
        purchase.setBuyer(user);
        purchase.setId(IdGenerator.generateId());
        purchase.setItem(item);
        purchase.setSeller(item.getSeller());
        purchase.setPrice(amount);
        purchase.setPurchaseDate(currentDate);

        purchaseRepository.save(purchase);

        Optional<Wallet> wallet = walletRepository.findById(user.getId());
        Wallet wallet_ = wallet.orElse(null);
        var saldo = wallet_.getSaldo() - amount ;
        wallet_.setSaldo(saldo);
        walletRepository.aggiornaSaldo(wallet_.getId(), saldo);


        itemRepository.aggiornaStato(item.getId(), String.valueOf(Dictionary.Status.IN_DELIVERY));

        return ResponseEntity.ok("oggetto venduto");
    }


}
