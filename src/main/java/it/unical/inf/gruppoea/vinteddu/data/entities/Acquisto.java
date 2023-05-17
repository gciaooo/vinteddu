package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "oggetti")
@Data
@NoArgsConstructor
public class Acquisto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "dataacquisto")
    private LocalDate dataAcquisto;

    @OneToOne
    @JoinColumn(name = "idoggetto", referencedColumnName = "id")
    private Oggetto oggetto;

    @ManyToOne
    @JoinColumn(name = "idacquirente", referencedColumnName = "id")
    private Utente acquirente;

    @ManyToOne
    @JoinColumn(name = "idvenditore", referencedColumnName = "id")
    private Utente venditore;

    @Column(name = "prezzo")
    private BigDecimal prezzo;
}
