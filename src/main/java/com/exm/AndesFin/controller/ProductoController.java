package com.exm.AndesFin.controller;

import com.exm.AndesFin.DTOs.ProductoDTO;
import com.exm.AndesFin.service.ProductoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @GetMapping
    public List<ProductoDTO> listarProductosActivos() {
        return productoService.listarActivos();
    }
}
