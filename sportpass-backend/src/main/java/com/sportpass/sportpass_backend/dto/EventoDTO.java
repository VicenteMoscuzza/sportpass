package com.sportpass.sportpass_backend.dto;

import java.time.LocalDateTime;
import java.util.List;
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
    @Data
    public static class EventoDetalle {
        private Long id;
        private String nombre;
        private String descripcion;
        private LocalDateTime fecha;
        private String estado;
        private String estadioNombre;
        private String estadioCiudad;
        private String estadioDireccion;
        private List<ZonaInfo> zonas;

        @Data
        public static class ZonaInfo {
            private Long id;
            private Long zonaId;
            private String zonaNombre;
            private Double precio;
            private Integer capacidadDisponible;
            private Boolean esGeneral;
        }
    }

    @Data
    public static class AsientoInfo {
        private Long id;
        private String fila;
        private Integer numero;
        private Boolean ocupado;
    }

    @Data
    public static class EventoCreateRequest {
        private String nombre;
        private String descripcion;
        private LocalDateTime fecha;
        private Long estadioId;
        private List<ZonaPrecios> zonaPrecios;
    
        @Data
        public static class ZonaPrecios {
            private Long zonaId;
            private Double precio;
            private Integer capacidadDisponible;
        }
    }

    @Data
    public static class EventoUpdateRequest {
        private String nombre;
        private String descripcion;
        private LocalDateTime fecha;
        private String estado;
    }
}