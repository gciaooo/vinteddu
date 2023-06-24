package it.unical.inf.gruppoea.vinteddu.controller;


import com.nimbusds.jose.JOSEException;
import it.unical.inf.gruppoea.vinteddu.data.dao.ItemDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.UserDao;
import it.unical.inf.gruppoea.vinteddu.data.dao.WalletDao;
import it.unical.inf.gruppoea.vinteddu.data.entities.Item;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import it.unical.inf.gruppoea.vinteddu.data.entities.Wallet;
import it.unical.inf.gruppoea.vinteddu.dto.UtenteDTO;
import it.unical.inf.gruppoea.vinteddu.dto.WalletDTO;
import it.unical.inf.gruppoea.vinteddu.security.TokenStore;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Optional;

@RestController
@RequestMapping(path="/api/v2", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class ProfileController {


    @Autowired
    private UserDao userRepository;
    @Autowired
    private WalletDao walletRepository;
    @Autowired
    private ItemDao itemRepository;



   @GetMapping("/user/{token}")
   @PreAuthorize("hasRole('ROLE_USER')")
   public ResponseEntity<UtenteDTO> getAccount(@PathVariable("token") String token){
       String username = null;
       try {
           username = TokenStore.getInstance().getUser(token);
       } catch (JOSEException e) {
           e.printStackTrace();
       } catch (ParseException e) {
           e.printStackTrace();
       }
       User account = userRepository.findByUsername(username);
       var utente = new UtenteDTO();

       utente.setId(account.getId());
       utente.setCognome(account.getFirstName());
       utente.setEmail(account.getEmail());
       utente.setDataNascita(account.getBirthDate());
       utente.setIndirizzo(account.getAddress());
       utente.setNome(account.getLastName());
       utente.setNumeroTelefono(account.getPhoneNumber());
       utente.setUsername(account.getUsername());

       return ResponseEntity.ok(utente);
   }

    @GetMapping("/wallet/{userId}")
    @PreAuthorize("hasRole('ROLE_USER')")
    public ResponseEntity<WalletDTO> getSaldo(@PathVariable("userId") Long id){

        Wallet wallet = walletRepository.findById(id).get();
        var wallet_dto = new WalletDTO();

        wallet_dto.setIdUtente(wallet.getIdUtente());
        wallet_dto.setSaldo(wallet.getSaldo());

        return ResponseEntity.ok(wallet_dto);
    }

//    @GetMapping("/users/{username}")
//    @PreAuthorize("#username.equals(authentication.principal.getUsername())")
//    public ResponseEntity<User> getAccount(@PathVariable("username") String username){
//        User account = userRepository.findByUsername(username);
//
//        return ResponseEntity.ok(account);
//    }




//    @GetMapping(path="/users/{username}")
//    @PreAuthorize("#username.equals(authentication.principal.getUsername())")
//    public String getUser(@PathVariable("username") String username) {
//        User user = userRepository.findByUsername(username);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("username", user.getUsername());
//        return jsonObject.toString();
//    }

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
