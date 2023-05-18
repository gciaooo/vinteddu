package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "oggetti")
@Data
@NoArgsConstructor
public class Item {

    public enum Status {
        ON_SALE,
        IN_DELIVERY,
        DELIVERED,
        ABORTED
    }

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 50)
    private String name;

    @Column(name = "descrizione", length = 500)
    private String description;

    @Column(name = "prezzo", nullable = false)
    private BigDecimal price;

    @Column(name = "datacreazione", nullable = false)
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato")
    private Status status;

    @ManyToOne
    @JoinColumn(name = "idutente", referencedColumnName = "id")
    private User seller;
    @Column(name = "immagine")
    private String mainImage;

    @OneToMany(mappedBy = "item")
    private List<Image> images;
}
