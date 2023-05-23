package it.unical.inf.gruppoea.vinteddu.repositories;

import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}