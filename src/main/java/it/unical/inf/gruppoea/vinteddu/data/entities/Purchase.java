package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Table(name = "oggetti")
@Data
@NoArgsConstructor
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "dataacquisto")
    private Date purchaseDate;

    @OneToOne
    @JoinColumn(name = "idoggetto", referencedColumnName = "id")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "idacquirente", referencedColumnName = "id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "idvenditore", referencedColumnName = "id")
    private User seller;

    @Column(name = "prezzo")
    private Double price;
}
