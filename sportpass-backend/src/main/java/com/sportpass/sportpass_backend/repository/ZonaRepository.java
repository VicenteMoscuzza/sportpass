package com.sportpass.sportpass_backend.repository;

import com.sportpass.sportpass_backend.model.Zona;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ZonaRepository extends JpaRepository<Zona, Long> {
    List<Zona> findByEstadioId(Long estadioId);
}