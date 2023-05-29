package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "wallet")
public class Wallet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_utente")
    private Long idUtente;

    @Column(name = "saldo")
    private Float saldo;

}
