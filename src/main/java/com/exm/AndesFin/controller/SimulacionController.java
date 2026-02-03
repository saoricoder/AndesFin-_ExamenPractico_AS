package com.exm.AndesFin.controller;

import com.exm.AndesFin.DTOs.SimulacionRequestDTO;
import com.exm.AndesFin.DTOs.SimulacionResponseDTO;
import com.exm.AndesFin.service.SimulacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/simulaciones")
public class SimulacionController {
    @Autowired
    private SimulacionService simulacionService;

    @PostMapping
    public ResponseEntity<SimulacionResponseDTO> realizarSimulacion(@RequestBody SimulacionRequestDTO request){
        SimulacionResponseDTO response = simulacionService.crearSimulacion(request);
        if (response.getMensaje() != null && response.getMensaje().contains("Insuficientes")){
            return ResponseEntity.badRequest().body(response);
        }else{
            return ResponseEntity.ok(response);
        }

    }

    @GetMapping("/{usuarioId}")
    public List<SimulacionResponseDTO> consultarHistorial(@PathVariable UUID usuarioId) {
        return simulacionService.consultarHistorialPorUsuario(usuarioId);
    }


}
