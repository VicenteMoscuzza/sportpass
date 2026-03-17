package com.sportpass.sportpass_backend.repository;

import com.sportpass.sportpass_backend.model.Entrada;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EntradaRepository extends JpaRepository<Entrada, Long> {
    List<Entrada> findByCompraId(Long compraId);
    Boolean existsByAsientoIdAndEventoZonaEventoId(Long asientoId, Long eventoId);
}