package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "preferiti")
@Data
@NoArgsConstructor
public class Favorites {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "id_utente")
    private Long id_Utente;

    @Column(name = "id_oggetto")
    private Long id_Oggetto;


}
