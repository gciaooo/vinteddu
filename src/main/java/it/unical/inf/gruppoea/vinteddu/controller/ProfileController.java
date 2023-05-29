package it.unical.inf.gruppoea.vinteddu.controller;


import it.unical.inf.gruppoea.vinteddu.data.dao.UserDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.WalletDao;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import it.unical.inf.gruppoea.vinteddu.data.entities.Wallet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path="/api/v1", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class ProfileController {


    @Autowired
    private UserDao userRepository;
    @Autowired
    private WalletDao walletRepository;

    @GetMapping("/{userId}/Account")
    public ResponseEntity<User> getAccount(@PathVariable("userId") Long userId){
        User account = userRepository.findById(userId);

        return ResponseEntity.ok(account);
    }

    @PostMapping("/Wallet/{userId}")
    public ResponseEntity<String> wallet_recharge(@RequestParam double amount, @PathVariable("userId") Long userId){
        try {

            walletRepository.aggiornaSaldo(userId, amount);

            return ResponseEntity.ok("Wallet ricaricato con successo");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Errore durante la ricarica del wallet");
        }
    }


}
