package it.unical.inf.gruppoea.vinteddu.controller;

import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@RestController
@RequestMapping(path="/api/v1", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class AuthenticationController {

    private final User userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(User userRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager) {
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
        UserAccount userAccount = new UserAccount(username, passwordEncoder.encode(password));
        userRepository.save(userAccount);
        return new ResponseEntity<>("registered", HttpStatus.OK);
    }

    @GetMapping(path="/users/{username}")
    @PreAuthorize("#username.equals(authentication.principal.getUsername()) or hasRole('ADMIN')")
    public String getUser(@PathVariable("username") String username) {
        UserAccount user = userRepository.findByUsername(username);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("username", user.getUsername());
        return jsonObject.toString();
    }

    @GetMapping(path="/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Iterable<UserAccount> users() {
        return userRepository.findAll();
    }

}