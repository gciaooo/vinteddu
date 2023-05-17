package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "immaginioggetti")
@Data
@NoArgsConstructor
public class Immagine {
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "immagine")
    private String immagine;

    @ManyToOne
    @JoinColumn(name = "oggetti", referencedColumnName = "id")
    private Oggetto oggetto;
}
