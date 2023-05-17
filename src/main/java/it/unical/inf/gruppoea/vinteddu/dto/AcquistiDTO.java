package it.unical.inf.gruppoea.vinteddu.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@ToString
@Data
public class AcquistiDTO {

    private int id;

    @NotNull
    private LocalDate dataAcquisto;

    @NotNull
    @Positive
    private int idOggetto;

    @NotNull
    @Positive
    private int idAcquirente;

    @NotNull
    @Positive
    private int idVenditore;

    @NotNull
    private BigDecimal prezzo;
}
