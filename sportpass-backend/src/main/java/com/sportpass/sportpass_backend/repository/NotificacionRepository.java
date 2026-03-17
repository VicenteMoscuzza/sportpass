package com.sportpass.sportpass_backend.repository;

import com.sportpass.sportpass_backend.model.Notificacion;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface NotificacionRepository extends JpaRepository<Notificacion, Long> {
    List<Notificacion> findByUsuarioIdOrderByFechaDesc(Long usuarioId);
    List<Notificacion> findByUsuarioIdAndLeida(Long usuarioId, Boolean leida);
}