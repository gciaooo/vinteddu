package it.unical.inf.gruppoea.vinteddu.controller;
import it.unical.inf.gruppoea.vinteddu.data.dao.ItemDao;
import it.unical.inf.gruppoea.vinteddu.data.entities.Item;
import it.unical.inf.gruppoea.vinteddu.dto.OggettoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;

@RestController
@RequestMapping(path="/api/v1/users", produces = "application/json")
@CrossOrigin(origins = "http://localhost:8080")
public class HomeController {



    @Autowired
    private ItemDao itemRepository;

    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItemByName(@RequestParam("nome") String nome) {
        List<Item> oggetti = itemRepository.findByNomeContaining(nome);
        return ResponseEntity.ok(oggetti);
    }

    @PostMapping("/add")
    public ResponseEntity<String> addOggetto(@RequestBody OggettoDTO oggettoDTO) {
        Item oggetto = new Item();
        oggetto.setId((long) oggettoDTO.getId());
        oggetto.setName(oggettoDTO.getNome());
        oggetto.setDescription(oggettoDTO.getDescrizione());
        oggetto.setPrice(oggettoDTO.getPrezzo());
        oggetto.setCreationDate(oggettoDTO.getDataCreazione());
        //oggetto.setStatus(oggettoDTO.getStato());
        //oggetto.setSeller(oggettoDTO.getIdUtente());



        Item savedOggetto = itemRepository.save(oggetto);

        if (savedOggetto != null) {
            return ResponseEntity.ok("Oggetto aggiunto con successo");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }




}
