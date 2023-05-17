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
public class Oggetto {

    public enum Status {
        ON_SALE,
        IN_DELIVERY,
        DELIVERED,
        ABORTED
    }

    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "nome", length = 50)
    private String nome;

    @Column(name = "descrizione", length = 500)
    private String descrizione;

    @Column(name = "prezzo", nullable = false)
    private BigDecimal prezzo;

    @Column(name = "datacreazione", nullable = false)
    private LocalDate dataCreazione;

    @Enumerated(EnumType.STRING)
    @Column(name = "stato")
    private Status stato;

    @ManyToOne
    @JoinColumn(name = "idutente", referencedColumnName = "id")
    private Utente venditore;
    @Column(name = "immagine")
    private String immagineMain;

    @OneToMany(mappedBy = "oggetto")
    private List<Immagine> immagini;
}
