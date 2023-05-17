package it.unical.inf.gruppoea.vinteddu.dto;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.JoinColumn;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@ToString
@Data
public class OggettoDTO {

    private int id;

    @NotBlank
    @Size(max = 50)
    private String nome;

    @NotBlank
    @Size(max = 500)
    private String descrizione;

    @NotNull
    @Positive
    private BigDecimal prezzo;

    @NotNull
    private LocalDate dataCreazione;

    @NotBlank
    private int stato;

    @NotNull
    @Positive
    private int idUtente;

    @ElementCollection
    @CollectionTable(name = "Immagini", joinColumns = @JoinColumn(name = "ID_Oggetto"))
    @Column(name = "URL")
    private List<String> immagini;
}
