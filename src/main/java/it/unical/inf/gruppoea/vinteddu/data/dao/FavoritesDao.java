package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavoritesDao extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    @Transactional
    @Modifying
    @Query("Select idOggetto From Favorites where idUtente=:idUtente ")
    List<Long> getListaPreferiti(@Param("idUtente") Long idUtente);


}