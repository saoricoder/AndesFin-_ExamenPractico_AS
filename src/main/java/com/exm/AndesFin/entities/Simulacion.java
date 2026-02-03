package com.exm.AndesFin.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "simulaciones")
@Data
public class Simulacion {

    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "usuario_id")
    private Usuario usuario;

    private LocalDateTime fechaSimulacion;

    private BigDecimal capitalDisponible;

    private BigDecimal gananciaTotal;

    @ManyToMany
    private List<ProductoFinanciero> productosSeleccionados;
}
