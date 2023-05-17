package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Acquisto;
import it.unical.inf.gruppoea.vinteddu.data.entities.Utente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

public interface AcquistiDao extends CrudRepository<Acquisto, Long>, JpaSpecificationExecutor<Acquisto> {
}
