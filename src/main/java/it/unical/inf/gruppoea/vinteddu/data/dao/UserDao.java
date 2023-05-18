package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);
}
