package com.exm.AndesFin.DTOs;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class SimulacionRequestDTO {
    private UUID usuarioId;
    private BigDecimal capitalDisponible;
    private List<ProductoDTO> productos;
}
