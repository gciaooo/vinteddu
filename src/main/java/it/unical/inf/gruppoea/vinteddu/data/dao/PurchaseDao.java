package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Purchase;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseDao extends CrudRepository<Purchase, Long>, JpaSpecificationExecutor<Purchase> {
}
