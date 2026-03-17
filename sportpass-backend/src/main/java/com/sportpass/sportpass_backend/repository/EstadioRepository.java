package com.sportpass.sportpass_backend.repository;

import com.sportpass.sportpass_backend.model.Estadio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EstadioRepository extends JpaRepository<Estadio, Long> {
}