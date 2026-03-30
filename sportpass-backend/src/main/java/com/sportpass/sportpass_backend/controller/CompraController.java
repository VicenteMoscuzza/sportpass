package com.sportpass.sportpass_backend.controller;

import com.sportpass.sportpass_backend.dto.CompraDTO;
import com.sportpass.sportpass_backend.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sportpass.sportpass_backend.service.PagoService;

@RestController
@RequestMapping("/api/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;
    private final PagoService pagoService;

    @PostMapping
    public ResponseEntity<CompraDTO.CompraResponse> crearCompra(
            @RequestBody CompraDTO.CompraRequest request) {
        return ResponseEntity.ok(compraService.crearCompra(request));
    }

    @PostMapping("/iniciar-pago")
    public ResponseEntity<CompraDTO.PagoResponse> iniciarPago(
            @RequestBody CompraDTO.PagoRequest request) {
        return ResponseEntity.ok(pagoService.crearPreferencia(request));
    }

    @GetMapping("/confirmar")
    public ResponseEntity<CompraDTO.CompraResponse> confirmarPago(
            @RequestParam String externalReference) {
        String[] partes = externalReference.split("\\|");
        CompraDTO.CompraRequest compraRequest = new CompraDTO.CompraRequest();
        compraRequest.setEventoZonaId(Long.parseLong(partes[0]));
        compraRequest.setAsientoId(partes[1].equals("null") ? null : Long.parseLong(partes[1]));
        compraRequest.setCantidad(Integer.parseInt(partes[2]));
        return ResponseEntity.ok(compraService.crearCompra(compraRequest));
    }
}