package com.exm.AndesFin.service;

import com.exm.AndesFin.DTOs.ProductoDTO;
import com.exm.AndesFin.entities.ProductoFinanciero;
import com.exm.AndesFin.repository.ProductoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductoService {
    @Autowired
    private ProductoRepository productoRepository;

    private List<ProductoDTO> listarActivos(){
        return productoRepository.findbyActivoTrue().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    private ProductoDTO convertToDTO(ProductoFinanciero productoFinanciero){
        ProductoDTO dto=new ProductoDTO();
        dto.setId(productoFinanciero.getId());
        dto.setNombre(productoFinanciero.getNombre());
        dto.setDescripcion(productoFinanciero.getDescripcion());
        dto.setCosto(productoFinanciero.getCosto());
        dto.setPorcentajeRetorno(productoFinanciero.getPorcentajeRetorno());
        dto.setActivo(productoFinanciero.isActivo());
        return dto;
    }
}

