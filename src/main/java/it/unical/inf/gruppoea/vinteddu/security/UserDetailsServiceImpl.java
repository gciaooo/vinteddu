package it.unical.inf.gruppoea.vinteddu.security;

import it.unical.inf.gruppoea.vinteddu.data.dao.UserDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private UserDao userRepository;

    public UserDetailsServiceImpl(UserDao userRepository) {
        this.userRepository = userRepository;
    }
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if("admin".equals(username))
            return new User(username, new BCryptPasswordEncoder(12).encode("strongPassword"), List.of(new SimpleGrantedAuthority("ROLE_ADMIN")));
        it.unical.inf.gruppoea.vinteddu.data.entities.User userAccount = userRepository.findByUsername(username);
        if(userAccount != null) return new User(username, userAccount.getPassword(), List.of(new SimpleGrantedAuthority("ROLE_USER")));
        throw new UsernameNotFoundException("User not found");
    }
}