package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Oggetto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface OggettoDao extends CrudRepository<Oggetto, Long>, JpaSpecificationExecutor<Oggetto> {
}
