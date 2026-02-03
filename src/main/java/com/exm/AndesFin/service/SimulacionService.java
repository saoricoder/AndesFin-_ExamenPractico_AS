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
    @Autowired
    private ProductoRepository productoRepository;

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
        List<ProductoFinanciero> productosEntidad = new ArrayList<>();
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
                
                // Buscar producto en DB si tiene ID, o crear referencia (simplificado)
                // En un escenario real, los productos del request deberían tener ID válido
                if(p.getId() != null) {
                    productoRepository.findById(p.getId()).ifPresent(productosEntidad::add);
                }

                costoTotal = costoTotal.add(p.getCosto());
                gananciaTotal = gananciaTotal.add(ganancia);
            }
        }

        // 3. Persistir simulación
        Simulacion simulacion = new Simulacion();
        simulacion.setUsuario(usuarioRepository.findById(request.getUsuarioId()).orElse(null));
        simulacion.setFechaSimulacion(LocalDateTime.now());
        simulacion.setCapitalDisponible(request.getCapitalDisponible());
        simulacion.setGananciaTotal(gananciaTotal);
        simulacion.setProductosSeleccionados(productosEntidad);
        
        simulacionRepository.save(simulacion);

        return construirRespuestaExitosa(request, seleccionados, costoTotal, gananciaTotal);
    }

    public List<SimulacionResponseDTO> consultarHistorialPorUsuario(UUID usuarioId) {
        List<Simulacion> simulaciones = simulacionRepository.findByUsuarioId(usuarioId);
        return simulaciones.stream().map(this::convertirADTO).collect(Collectors.toList());
    }

    private SimulacionResponseDTO convertirADTO(Simulacion s) {
        SimulacionResponseDTO dto = new SimulacionResponseDTO();
        dto.setId(s.getId());
        dto.setUsuarioId(s.getUsuario() != null ? s.getUsuario().getId() : null);
        dto.setFechaSimulacion(s.getFechaSimulacion());
        dto.setCapitalDisponible(s.getCapitalDisponible());
        dto.setGananciaTotal(s.getGananciaTotal());
        
        // Calcular costo total y capital restante basado en la entidad
        BigDecimal costoTotal = s.getProductosSeleccionados().stream()
                .map(ProductoFinanciero::getCosto)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        dto.setCostoTotal(costoTotal);
        dto.setCapitalRestante(s.getCapitalDisponible().subtract(costoTotal));

        // Mapear productos seleccionados
        List<ProductoSeleccionadoDTO> productosDTO = s.getProductosSeleccionados().stream().map(p -> {
            ProductoSeleccionadoDTO pDto = new ProductoSeleccionadoDTO();
            pDto.setNombre(p.getNombre());
            pDto.setPrecio(p.getCosto());
            pDto.setPorcentajeGanancia(p.getPorcentajeRetorno());
            pDto.setGananciaEsperada(p.getCosto().multiply(p.getPorcentajeRetorno()).divide(new BigDecimal("100"), 2, RoundingMode.HALF_UP));
            return pDto;
        }).collect(Collectors.toList());
        
        dto.setProductosSeleccionados(productosDTO);
        return dto;
    }

    private SimulacionResponseDTO manejarFondosInsuficientes(SimulacionRequestDTO request) {
        SimulacionResponseDTO error = new SimulacionResponseDTO();
        error.setMensaje("Fondos insuficientes");
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
