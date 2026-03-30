package com.sportpass.sportpass_backend.service;

import com.sportpass.sportpass_backend.dto.EventoDTO;
import com.sportpass.sportpass_backend.repository.AsientoRepository;
import com.sportpass.sportpass_backend.repository.EntradaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AsientoService {

    private final AsientoRepository asientoRepository;
    private final EntradaRepository entradaRepository;

    public List<EventoDTO.AsientoInfo> getAsientosPorZona(Long zonaId, Long eventoId) {
        return asientoRepository.findByZonaId(zonaId).stream()
                .map(asiento -> {
                    EventoDTO.AsientoInfo dto = new EventoDTO.AsientoInfo();
                    dto.setId(asiento.getId());
                    dto.setFila(asiento.getFila());
                    dto.setNumero(asiento.getNumero());
                    dto.setOcupado(entradaRepository
                            .existsByAsientoIdAndEventoZonaEventoId(asiento.getId(), eventoId));
                    return dto;
                })
                .toList();
    }
}