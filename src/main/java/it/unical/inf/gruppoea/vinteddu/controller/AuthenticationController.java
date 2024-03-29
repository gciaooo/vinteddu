package it.unical.inf.gruppoea.vinteddu.controller;

import com.nimbusds.jose.JOSEException;
import it.unical.inf.gruppoea.vinteddu.data.dao.UserDao;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import it.unical.inf.gruppoea.vinteddu.security.TokenStore;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.nio.file.attribute.UserPrincipal;
import java.util.Map;

@RestController
@RequestMapping(path="/api/v1", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthenticationController {

    private final UserDao userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(UserDao userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping(path = "/authenticate")
    @ResponseStatus(HttpStatus.OK)
    public void authenticate(@RequestParam("username") String username, @RequestParam("password") String password, HttpServletResponse response) throws JOSEException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        String token = TokenStore.getInstance().createToken(Map.of("username",  username));
        response.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }

    @PostMapping(path="/register")
    public ResponseEntity<String> register(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {
        if("admin".equals(username) || userRepository.findByUsername(username) != null)
            return new ResponseEntity<>("existing username", HttpStatus.CONFLICT);
        User userAccount = new User(username, passwordEncoder.encode(password));
        userRepository.save(userAccount);
        //logger.info("Questo è un messaggio di log informativo");
        return new ResponseEntity<>("registered", HttpStatus.OK);
    }

//    @GetMapping(path="/users/{username}")
//    @PreAuthorize("#username.equals(authentication.principal.getUsername()) or hasRole('ADMIN')")
//    public String getUser(@PathVariable("username") String username) {
//        User user = userRepository.findByUsername(username);
//        JSONObject jsonObject = new JSONObject();
//        jsonObject.put("username", user.getUsername());
//        return jsonObject.toString();
//    }

    @GetMapping(path="/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<User> users() {
        return userRepository.findAll();
    }



    @GetMapping("/current")
    public ResponseEntity<Long> getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            User user = (User) authentication.getPrincipal();
            Long userId = user.getId();
            return ResponseEntity.ok(userId);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }



//    @PostMapping("/logout")
//    public ResponseEntity<String> logout(HttpServletRequest request) {
//        HttpSession session = request.getSession(false);
//        if (session != null) {
//            session.removeAttribute("token");
//            session.invalidate();
//        }
//
//        return ResponseEntity.ok("Logged out successfully");
//    }

}