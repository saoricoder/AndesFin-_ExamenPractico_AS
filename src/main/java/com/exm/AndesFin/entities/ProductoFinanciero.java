package com.exm.AndesFin.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

public class ProductoFinanciero {
    @Id
    @GeneratedValue
    private UUID id;
    private String nombre;
    private String descripcion;
    private BigDecimal costo;
    private BigDecimal porcentaje_retorno;
    private boolean activo;
}
