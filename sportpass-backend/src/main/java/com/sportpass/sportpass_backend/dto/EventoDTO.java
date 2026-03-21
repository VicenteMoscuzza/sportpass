package com.sportpass.sportpass_backend.dto;

import java.time.LocalDateTime;

import lombok.Data;

public class EventoDTO {

    @Data
    public static class EventoResumen {
        private Long id;
        private String nombre;
        private String descripcion;
        private LocalDateTime fecha;
        private String estado;
        private String estadioNombre;
        private String estadioCiudad;
        private Double precioDesde;
    }
}