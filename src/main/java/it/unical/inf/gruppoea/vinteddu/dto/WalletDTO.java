package it.unical.inf.gruppoea.vinteddu.dto;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@ToString
@Data
public class WalletDTO {

    @NotNull
    private Long idUtente;

    @NotNull
    private Integer saldo;

}
