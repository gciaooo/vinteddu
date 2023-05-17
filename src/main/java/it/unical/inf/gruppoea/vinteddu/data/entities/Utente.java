package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "utenti")
@Data
@NoArgsConstructor
public class Utente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome")
    private String nome;


    @Column(name = "cognome")
    private String cognome;
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "datanascita")
    private LocalDate dataNascita;

    @Column(name = "indirizzo")
    private String indirizzo;

    @Column(name = "numerotelefono")
    private String numeroTelefono;

    @OneToMany(mappedBy = "acquirente")
    private List<Acquisto> acquisti;

    @OneToMany(mappedBy = "venditore")
    private List<Acquisto> vendite;

    @OneToMany(mappedBy = "venditore")
    private List<Oggetto> oggettiInVendita;
}
