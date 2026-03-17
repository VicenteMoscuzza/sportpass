package com.sportpass.sportpass_backend.repository;

import com.sportpass.sportpass_backend.model.Asiento;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AsientoRepository extends JpaRepository<Asiento, Long> {
    List<Asiento> findByZonaId(Long zonaId);
}