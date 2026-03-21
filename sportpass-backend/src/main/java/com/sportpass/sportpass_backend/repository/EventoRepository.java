package com.sportpass.sportpass_backend.repository;

import com.sportpass.sportpass_backend.model.Evento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface EventoRepository extends JpaRepository<Evento, Long> {
    List<Evento> findByEstado(Evento.Estado estado);
    List<Evento> findByEstadoAndFechaAfterOrderByFechaAsc(Evento.Estado estado, LocalDateTime fecha);
}