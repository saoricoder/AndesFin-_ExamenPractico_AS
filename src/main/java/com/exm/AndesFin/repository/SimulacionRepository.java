package com.exm.AndesFin.repository;

import com.exm.AndesFin.entities.Simulacion;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SimulacionRepository extends JpaRepository<Simulacion, UUID> {
    List<Simulacion> findByUsuarioId(UUID usuarioId);
}
