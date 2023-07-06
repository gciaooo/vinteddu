package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Item;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import it.unical.inf.gruppoea.vinteddu.dto.Dictionary.Dictionary;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemDao extends CrudRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    List<Item> getItemBySeller(User venditore);

    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String desc);

    List<Item> findByNameContainingIgnoreCase(String nome);


    @Transactional
    @Modifying
    @Query("UPDATE Item w SET w.status = :nuovoStato WHERE w.id = :itemId")
    void aggiornaStato(@Param("itemId") Long itemId, @Param("nuovoStato") Dictionary.Status nuovoStato);




}
