package com.sportpass.sportpass_backend.controller;

import com.sportpass.sportpass_backend.repository.EstadioRepository;
import com.sportpass.sportpass_backend.repository.ZonaRepository;
import com.sportpass.sportpass_backend.model.Estadio;
import com.sportpass.sportpass_backend.model.Zona;
import com.sportpass.sportpass_backend.dto.EventoDTO;
import com.sportpass.sportpass_backend.service.EventoService;
import com.sportpass.sportpass_backend.service.AsientoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;
    private final AsientoService asientoService;
    private final EstadioRepository estadioRepository;
    private final ZonaRepository zonaRepository;

    @GetMapping
    public ResponseEntity<List<EventoDTO.EventoResumen>> getProximosEventos() {
        return ResponseEntity.ok(eventoService.getProximosEventos());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<EventoDTO.EventoDetalle> getEventoById(@PathVariable Long id) {
        return ResponseEntity.ok(eventoService.getEventoById(id));
    }

    @GetMapping("/{eventoId}/zonas/{zonaId}/asientos")
    public ResponseEntity<List<EventoDTO.AsientoInfo>> getAsientos(
            @PathVariable Long eventoId,
            @PathVariable Long zonaId) {
        return ResponseEntity.ok(asientoService.getAsientosPorZona(zonaId, eventoId));
    }

    @PostMapping("/admin/eventos")
    public ResponseEntity<EventoDTO.EventoDetalle> crearEvento(
            @RequestBody EventoDTO.EventoCreateRequest request) {
        return ResponseEntity.ok(eventoService.crearEvento(request));
    }

    @PutMapping("/admin/eventos/{id}")
    public ResponseEntity<EventoDTO.EventoDetalle> actualizarEvento(
            @PathVariable Long id,
            @RequestBody EventoDTO.EventoUpdateRequest request) {
        return ResponseEntity.ok(eventoService.actualizarEvento(id, request));
    }

    @DeleteMapping("/admin/eventos/{id}")
    public ResponseEntity<Void> eliminarEvento(@PathVariable Long id) {
        eventoService.eliminarEvento(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/admin/estadios")
    public ResponseEntity<List<Estadio>> getEstadios() {
        return ResponseEntity.ok(estadioRepository.findAll());
    }

    @GetMapping("/admin/estadios/{id}/zonas")
    public ResponseEntity<List<Zona>> getZonasPorEstadio(@PathVariable Long id) {
        return ResponseEntity.ok(zonaRepository.findByEstadioId(id));
    }
}
