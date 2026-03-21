package com.sportpass.sportpass_backend.service;

import com.sportpass.sportpass_backend.dto.EventoDTO;
import com.sportpass.sportpass_backend.model.Evento;
import com.sportpass.sportpass_backend.repository.EventoRepository;
import com.sportpass.sportpass_backend.repository.EventoZonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EventoService {

    private final EventoRepository eventoRepository;
    private final EventoZonaRepository eventoZonaRepository;

    public List<EventoDTO.EventoResumen> getProximosEventos() {
        List<Evento> eventos = eventoRepository
                .findByEstadoAndFechaAfterOrderByFechaAsc(
                        Evento.Estado.ACTIVO,
                        LocalDateTime.now()
                );

        return eventos.stream().map(this::toResumen).toList();
    }

    private EventoDTO.EventoResumen toResumen(Evento evento) {
        EventoDTO.EventoResumen dto = new EventoDTO.EventoResumen();
        dto.setId(evento.getId());
        dto.setNombre(evento.getNombre());
        dto.setDescripcion(evento.getDescripcion());
        dto.setFecha(evento.getFecha());
        dto.setEstado(evento.getEstado().name());
        dto.setEstadioNombre(evento.getEstadio().getNombre());
        dto.setEstadioCiudad(evento.getEstadio().getCiudad());

        eventoZonaRepository.findByEventoId(evento.getId())
                .stream()
                .map(ez -> ez.getPrecio().doubleValue())
                .min(Double::compareTo)
                .ifPresent(dto::setPrecioDesde);

        return dto;
    }
}