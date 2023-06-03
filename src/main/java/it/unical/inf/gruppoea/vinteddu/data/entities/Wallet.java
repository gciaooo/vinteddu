package it.unical.inf.gruppoea.vinteddu.data.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "wallet")
@Data
@NoArgsConstructor
public class Wallet {
    @Id
    @Column(name = "id_utente")
    private Long idUtente;

    @Column(name = "saldo")
    private Double saldo;

}
