package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Item;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ItemDao extends CrudRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    List<Item> getItemBySeller(User venditore);

    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String desc);

    List<Item> findByNameContainingIgnoreCase(String nome);

    @Modifying
    @Query("UPDATE oggetti w SET w.stato = :nuovoStato WHERE w.id = :itemId")
    void aggiornaStato(@Param("itemId") Long itemId, @Param("nuovoStato") String nuovoStato);



}
