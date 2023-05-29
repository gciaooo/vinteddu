package it.unical.inf.gruppoea.vinteddu.data.dao;

import it.unical.inf.gruppoea.vinteddu.data.entities.Wallet;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface WalletDao extends CrudRepository<Wallet, Long>, JpaSpecificationExecutor<Wallet>{

    @Modifying
    @Query("UPDATE Wallet w SET w.saldo = :nuovoSaldo WHERE w.id = :walletId")
    void aggiornaSaldo(@Param("walletId") Long walletId, @Param("nuovoSaldo") Double nuovoSaldo);

}
