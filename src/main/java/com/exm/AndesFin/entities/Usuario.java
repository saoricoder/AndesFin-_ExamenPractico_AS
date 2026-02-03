package com.exm.AndesFin.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name="usuarios")
@Data

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String nombre;
    @Column(unique = true)
    private String email;
    private BigDecimal capitalDisponible;

}
