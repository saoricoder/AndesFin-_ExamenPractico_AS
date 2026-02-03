package com.exm.AndesFin.service;

import com.exm.AndesFin.DTOs.UsuarioDTO;
import com.exm.AndesFin.entities.Usuario;
import com.exm.AndesFin.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<UsuarioDTO> listarTodos(){
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }
    private UsuarioDTO convertToDTO(Usuario usuario){
        UsuarioDTO dto=new UsuarioDTO();
        dto.setId(usuario.getId());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setCapitalDisponible(usuario.getCapitalDisponible());
        return dto;
    }

}
