package com.inventoryservice.repository;

import com.inventoryservice.entity.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByStockName(String stockName);

    @Query("SELECT s.stockQuantity FROM Stock s WHERE s.stockId = :stockId")
    Integer findStockQuantityByStockId(@Param("stockId") Long stockId);

    @Modifying
    @Query("""
            UPDATE Stock s 
            SET s.stockQuantity = s.stockQuantity - :deduct
            WHERE s.stockId = :stockId AND s.stockQuantity >= :deduct
            """)
    int decrementStockIfEnough(@Param("stockId") Long stockId, @Param("deduct") Integer deduct);
}
