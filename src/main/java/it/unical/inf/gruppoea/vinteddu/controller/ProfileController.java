package it.unical.inf.gruppoea.vinteddu.controller;


import it.unical.inf.gruppoea.vinteddu.data.dao.UserDao;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/api/v1", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class ProfileController {


    @Autowired
    private UserDao userRepository;

    @GetMapping("/{userId}/Account")
    public ResponseEntity<User> getAccount(@PathVariable("userId") Long userId){
        User account = userRepository.findById(userId);

        return ResponseEntity.ok(account);
    }

    @PostMapping("/{userId}/Wallet")
    public ResponseEntity<String> wallet_recharge(@RequestParam double amount){


        return new ResponseEntity<>("registered", HttpStatus.OK);
    }


}
