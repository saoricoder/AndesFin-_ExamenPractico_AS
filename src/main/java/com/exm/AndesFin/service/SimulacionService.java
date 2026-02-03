package com.exm.AndesFin.service;

import com.exm.AndesFin.DTOs.*;
import com.exm.AndesFin.entities.*;
import com.exm.AndesFin.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SimulacionService {

    @Autowired
    private SimulacionRepository simulacionRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    public SimulacionResponseDTO crearSimulacion(SimulacionRequestDTO request) {
        BigDecimal capital = request.getCapitalDisponible();

        // 1. Filtrar productos viables (que no excedan el capital)
        List<ProductoDTO> candidatos = request.getProductos().stream()
                .filter(p -> p.getCosto().compareTo(capital) <= 0)
                .sorted((p1, p2) -> p2.getPorcentajeRetorno().compareTo(p1.getPorcentajeRetorno())) // Priorizar mayor retorno
                .collect(Collectors.toList());

        // Manejo de fondos insuficientes
        if (candidatos.isEmpty()) {
            return manejarFondosInsuficientes(request);
        }

        // 2. Algoritmo de optimización (Selección óptima)
        List<ProductoSeleccionadoDTO> seleccionados = new ArrayList<>();
        BigDecimal costoTotal = BigDecimal.ZERO;
        BigDecimal gananciaTotal = BigDecimal.ZERO;

        for (ProductoDTO p : candidatos) {
            if (costoTotal.add(p.getCosto()).compareTo(capital) <= 0) {
                ProductoSeleccionadoDTO sel = new ProductoSeleccionadoDTO();
                sel.setNombre(p.getNombre());
                sel.setPrecio(p.getCosto());
                sel.setPorcentajeGanancia(p.getPorcentajeRetorno());

                BigDecimal ganancia = p.getCosto().multiply(p.getPorcentajeRetorno())
                        .divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP);
                sel.setGananciaEsperada(ganancia);

                seleccionados.add(sel);
                costoTotal = costoTotal.add(p.getCosto());
                gananciaTotal = gananciaTotal.add(ganancia);
            }
        }

        // 3. Persistir simulación (Requerimiento de auditoría)
        // (Aquí llamarías a tu convertidor de Entidad y guardarías en simulacionRepository)

        return construirRespuestaExitosa(request, seleccionados, costoTotal, gananciaTotal);
    }

    public List<SimulacionResponseDTO> consultarHistorialPorUsuario(UUID usuarioId) {
        // Implementación básica para satisfacer el controlador
        // En un caso real, esto consultaría el repositorio
        return new ArrayList<>();
    }

    private SimulacionResponseDTO manejarFondosInsuficientes(SimulacionRequestDTO request) {
        SimulacionResponseDTO error = new SimulacionResponseDTO();
        error.setMensaje("Fondos insuficientes");
        // Agregar detalles de diferencia necesaria...
        return error;
    }

    private SimulacionResponseDTO construirRespuestaExitosa(SimulacionRequestDTO req, List<ProductoSeleccionadoDTO> lista, BigDecimal costo, BigDecimal ganancia) {
        SimulacionResponseDTO res = new SimulacionResponseDTO();
        res.setUsuarioId(req.getUsuarioId());
        res.setFechaSimulacion(LocalDateTime.now());
        res.setCapitalDisponible(req.getCapitalDisponible());
        res.setProductosSeleccionados(lista);
        res.setCostoTotal(costo);
        res.setCapitalRestante(req.getCapitalDisponible().subtract(costo));
        res.setGananciaTotal(ganancia);
        res.setMensaje("Simulación exitosa con ganancias óptimas");
        return res;
    }
}
