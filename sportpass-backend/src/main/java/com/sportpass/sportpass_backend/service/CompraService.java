package com.sportpass.sportpass_backend.service;

import com.sportpass.sportpass_backend.dto.CompraDTO;
import com.sportpass.sportpass_backend.model.*;
import com.sportpass.sportpass_backend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Sort;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final EntradaRepository entradaRepository;
    private final EventoZonaRepository eventoZonaRepository;
    private final AsientoRepository asientoRepository;
    private final UsuarioRepository usuarioRepository;

    @Transactional
    public CompraDTO.CompraResponse crearCompra(CompraDTO.CompraRequest request) {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        EventoZona eventoZona = eventoZonaRepository.findById(request.getEventoZonaId())
                .orElseThrow(() -> new RuntimeException("Zona no encontrada"));

        boolean esGeneral = eventoZona.getZona().getNombre().toLowerCase().contains("vip") == false &&
                            eventoZona.getZona().getNombre().toLowerCase().contains("palco") == false;

        // Crear compra
        Compra compra = new Compra();
        compra.setUsuario(usuario);
        compra.setEstado(Compra.Estado.APROBADA);
        compra.setTotal(eventoZona.getPrecio().multiply(BigDecimal.valueOf(request.getCantidad())));
        compra = compraRepository.save(compra);

        List<Entrada> entradas = new ArrayList<>();

        if (esGeneral) {
            // Entrada general — buscar asiento GENERAL de la zona
            Asiento asientoGeneral = asientoRepository.findByZonaId(eventoZona.getZona().getId())
                    .stream()
                    .filter(a -> a.getFila().equals("GENERAL"))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Asiento general no encontrado"));

            // Verificar disponibilidad
            if (eventoZona.getCapacidadDisponible() < request.getCantidad()) {
                throw new RuntimeException("No hay suficiente disponibilidad");
            }

            for (int i = 0; i < request.getCantidad(); i++) {
                Entrada entrada = new Entrada();
                entrada.setCompra(compra);
                entrada.setAsiento(asientoGeneral);
                entrada.setEventoZona(eventoZona);
                entrada.setCodigoQr(UUID.randomUUID().toString());
                entradas.add(entradaRepository.save(entrada));
            }

            // Descontar disponibilidad
            eventoZona.setCapacidadDisponible(eventoZona.getCapacidadDisponible() - request.getCantidad());
            eventoZonaRepository.save(eventoZona);

        } else {
            // VIP — asiento específico
            Asiento asiento = asientoRepository.findById(request.getAsientoId())
                    .orElseThrow(() -> new RuntimeException("Asiento no encontrado"));

            if (entradaRepository.existsByAsientoIdAndEventoZonaEventoId(
                    asiento.getId(), eventoZona.getEvento().getId())) {
                throw new RuntimeException("El asiento ya está ocupado");
            }

            Entrada entrada = new Entrada();
            entrada.setCompra(compra);
            entrada.setAsiento(asiento);
            entrada.setEventoZona(eventoZona);
            entrada.setCodigoQr(UUID.randomUUID().toString());
            entradas.add(entradaRepository.save(entrada));

            eventoZona.setCapacidadDisponible(eventoZona.getCapacidadDisponible() - 1);
            eventoZonaRepository.save(eventoZona);
        }

        // Armar respuesta
        CompraDTO.CompraResponse response = new CompraDTO.CompraResponse();
        response.setId(compra.getId());
        response.setEstado(compra.getEstado().name());
        response.setTotal(compra.getTotal().doubleValue());
        response.setEntradas(entradas.stream().map(e -> {
            CompraDTO.CompraResponse.EntradaInfo info = new CompraDTO.CompraResponse.EntradaInfo();
            info.setId(e.getId());
            info.setZonaNombre(e.getEventoZona().getZona().getNombre());
            info.setFila(e.getAsiento().getFila());
            info.setNumero(e.getAsiento().getNumero());
            info.setCodigoQr(e.getCodigoQr());
            return info;
        }).toList());

        return response;
    }

    @Transactional(readOnly = true)
    public List<CompraDTO.CompraAdminResumen> listarComprasAdmin() {
        return compraRepository.findAll(Sort.by(Sort.Direction.DESC, "fecha")).stream()
                .map(this::toCompraAdminResumen)
                .toList();
    }

    private CompraDTO.CompraAdminResumen toCompraAdminResumen(Compra c) {
        CompraDTO.CompraAdminResumen dto = new CompraDTO.CompraAdminResumen();
        dto.setId(c.getId());
        dto.setUsuarioEmail(c.getUsuario().getEmail());
        dto.setUsuarioNombre(c.getUsuario().getNombre());
        dto.setFecha(c.getFecha());
        dto.setEstado(c.getEstado() != null ? c.getEstado().name() : null);
        dto.setTotal(c.getTotal() != null ? c.getTotal().doubleValue() : null);

        List<Entrada> entradas = c.getEntradas();
        if (entradas == null || entradas.isEmpty()) {
            dto.setEntradas(Collections.emptyList());
            return dto;
        }

        dto.setEntradas(entradas.stream().map(this::toEntradaAdminResumen).toList());
        return dto;
    }

    private CompraDTO.EntradaAdminResumen toEntradaAdminResumen(Entrada e) {
        CompraDTO.EntradaAdminResumen dto = new CompraDTO.EntradaAdminResumen();
        dto.setId(e.getId());
        dto.setEventoNombre(e.getEventoZona().getEvento().getNombre());
        dto.setZonaNombre(e.getEventoZona().getZona().getNombre());
        dto.setFila(e.getAsiento().getFila());
        dto.setNumero(e.getAsiento().getNumero());
        dto.setCodigoQr(e.getCodigoQr());
        return dto;
    }
}