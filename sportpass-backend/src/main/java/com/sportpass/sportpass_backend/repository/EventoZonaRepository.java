package com.sportpass.sportpass_backend.repository;

import com.sportpass.sportpass_backend.model.EventoZona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EventoZonaRepository extends JpaRepository<EventoZona, Long> {
    List<EventoZona> findByEventoId(Long eventoId);
}
