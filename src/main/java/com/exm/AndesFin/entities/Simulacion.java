package com.exm.AndesFin.entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.util.UUID;

public class Simulacion {

    @Id
    @GeneratedValue
    private UUID id;
    private  usuario_id;
    private String fecha_simulacion;
    private BigDecimal capital_disponible;
    private BigDecimal ganacia_total;
    private  productos_seleccionado;
}
