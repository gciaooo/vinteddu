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
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "username")
    private String username;
    @Column(name = "nome")
    private String firstName;
    @Column(name = "cognome")
    private String lastName;
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "datanascita")
    private LocalDate birthDate;

    @Column(name = "indirizzo")
    private String address;

    @Column(name = "numerotelefono")
    private String phoneNumber;

    @OneToMany(mappedBy = "buyer")
    private List<Purchase> itemsBought;

    @OneToMany(mappedBy = "seller")
    private List<Purchase> itemsSold;

    @OneToMany(mappedBy = "seller")
    private List<Item> itemsOnSale;
}
