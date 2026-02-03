package com.exm.AndesFin.DTOs;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
public class SimulacionResponseDTO {
    private UUID id;
    private UUID usuarioId;
    private BigDecimal costoTotal;
    private BigDecimal capitalRestante;
    private BigDecimal retornoTotalPorcentaje;
    private LocalDateTime fechaSimulacion;
    private BigDecimal capitalDisponible;
    private BigDecimal gananciaTotal;
    private List<ProductoSeleccionadoDTO> productosSeleccionados;
    private String mensaje;
}
