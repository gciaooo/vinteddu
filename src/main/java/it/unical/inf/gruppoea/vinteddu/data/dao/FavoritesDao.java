package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Favorites;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface FavoritesDao extends JpaRepository<Favorites, Long>, JpaSpecificationExecutor<Favorites> {

    @Transactional
    @Modifying
    @Query("Select id_Oggetto From Favorites where id_Utente=:id_Utente ")
    List<Long> getListaPreferiti(@Param("id_Utente") Long id_utente);


}