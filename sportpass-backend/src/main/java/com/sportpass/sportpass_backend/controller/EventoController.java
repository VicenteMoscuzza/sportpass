package com.sportpass.sportpass_backend.controller;

import com.sportpass.sportpass_backend.dto.EventoDTO;
import com.sportpass.sportpass_backend.service.EventoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/eventos")
@RequiredArgsConstructor
public class EventoController {

    private final EventoService eventoService;

    @GetMapping
    public ResponseEntity<List<EventoDTO.EventoResumen>> getProximosEventos() {
        return ResponseEntity.ok(eventoService.getProximosEventos());
    }
}