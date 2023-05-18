package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Oggetto;
import it.unical.inf.gruppoea.vinteddu.data.entities.Utente;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OggettoDao extends CrudRepository<Oggetto, Long>, JpaSpecificationExecutor<Oggetto> {
    List<Oggetto> getOggettosByVenditore(Utente venditore);

    List<Oggetto> findByNomeContainingIgnoreCaseOrDescrizioneContainingIgnoreCase(String nome, String descrizione);
}
