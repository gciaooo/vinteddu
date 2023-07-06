package it.unical.inf.gruppoea.vinteddu.data.entities;

import it.unical.inf.gruppoea.vinteddu.dto.Dictionary.Dictionary;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "oggetti")
@Data
@NoArgsConstructor
public class Item {




    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "nome", length = 50)
    private String name;

    @Column(name = "descrizione", length = 500)
    private String description;

    @Column(name = "prezzo", nullable = false)
    private Integer price;

    @Column(name = "datacreazione", nullable = false)
    private LocalDate creationDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato")
    private Dictionary.Status status;

    @ManyToOne
    @JoinColumn(name = "idutente", referencedColumnName = "id")
    private User seller;



    @Column(name = "immagini")
    private String mainImage;

//    @OneToMany(mappedBy = "item", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Image> images = new ArrayList<>();



    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", creationDate=" + creationDate +
                ", status=" + status +
                ", seller=" + seller +
                ", mainImage='" + mainImage + '\'' +
                '}';
    }

    //public List<Image> getComments() { return Collections.unmodifiableList(images); }

}
