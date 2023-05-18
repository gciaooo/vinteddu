package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Item;
import it.unical.inf.gruppoea.vinteddu.data.entities.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ItemDao extends CrudRepository<Item, Long>, JpaSpecificationExecutor<Item> {
    List<Item> getItemBySeller(User venditore);

    List<Item> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String desc);
}
