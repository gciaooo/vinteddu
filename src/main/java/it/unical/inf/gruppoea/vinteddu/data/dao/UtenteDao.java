package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Oggetto;
import it.unical.inf.gruppoea.vinteddu.data.entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UtenteDao extends JpaRepository<Utente, Integer>, JpaSpecificationExecutor<Utente> {
    Utente findById(Long id);
}
