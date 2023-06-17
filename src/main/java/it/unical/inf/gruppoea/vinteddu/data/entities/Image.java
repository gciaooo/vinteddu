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

    @ManyToOne(fetch = FetchType.LAZY)
   // @JoinColumn(name = "idoggetto", referencedColumnName = "id")
    private Item item;


    void setItem(Item item ) { this.item = item;}
    public Item getItem(){return item;}
}
