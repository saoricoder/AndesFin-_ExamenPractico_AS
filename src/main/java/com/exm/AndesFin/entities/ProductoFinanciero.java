package com.exm.AndesFin.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "productos_financieros")
@Data
public class ProductoFinanciero {
    @Id
    @GeneratedValue
    private UUID id;
    private String nombre;
    private String descripcion;
    private BigDecimal costo;
    private BigDecimal porcentajeRetorno;
    private boolean activo;
}
