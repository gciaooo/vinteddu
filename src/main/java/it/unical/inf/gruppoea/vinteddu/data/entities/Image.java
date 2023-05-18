package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "immaginioggetti")
@Data
@NoArgsConstructor
public class Image {
    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "immagine")
    private String image;

    @ManyToOne
    @JoinColumn(name = "oggetti", referencedColumnName = "id")
    private Item item;
}
