package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Purchase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseDao extends CrudRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
    List<Purchase> findByBuyerId(Long userId);
}
