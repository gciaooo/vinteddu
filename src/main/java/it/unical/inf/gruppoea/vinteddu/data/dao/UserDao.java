package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {
    User findById(Long id);

    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhoneNumber(String phoneNumber);


    @Transactional
    @Modifying
    @Query("UPDATE User w SET w.password = :nuovaPassword WHERE w.username = :username")
    void aggiornaPassword(@Param("username") String username, @Param("nuovaPassword") String nuovaPassword);
}