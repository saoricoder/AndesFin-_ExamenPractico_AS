package com.exm.AndesFin.DTOs;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class ProductoSeleccionadoDTO {
    private String nombre;
    private BigDecimal precio;
    private BigDecimal porcentajeGanancia;
    private BigDecimal gananciaEsperada;
}
