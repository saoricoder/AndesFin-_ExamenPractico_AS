package com.exm.AndesFin.repository;

import com.exm.AndesFin.entities.ProductoFinanciero;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductoRepository extends JpaRepository<ProductoFinanciero, UUID> {
    List<ProductoFinanciero> findByActivoTrue();
}
