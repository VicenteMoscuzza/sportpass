package com.sportpass.sportpass_backend.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

public class CompraDTO {

    @Data
    public static class CompraRequest {
        private Long eventoZonaId;
        private Long asientoId; // null si es entrada general
        private Integer cantidad;
    }

    @Data
    public static class CompraResponse {
        private Long id;
        private String estado;
        private Double total;
        private List<EntradaInfo> entradas;

        @Data
        public static class EntradaInfo {
            private Long id;
            private String zonaNombre;
            private String fila;
            private Integer numero;
            private String codigoQr;
        }
    }

    @Data
    public static class PagoRequest {
        private Long eventoZonaId;
        private Long asientoId;
        private Integer cantidad;
    }

    @Data  
    public static class PagoResponse {
        private String checkoutUrl;
        private String preferenceId;
    }

    /** Compra + entradas para panel admin (GET /api/admin/compras). */
    @Data
    public static class CompraAdminResumen {
        private Long id;
        private String usuarioEmail;
        private String usuarioNombre;
        private LocalDateTime fecha;
        private String estado;
        private Double total;
        private List<EntradaAdminResumen> entradas;
    }

    @Data
    public static class EntradaAdminResumen {
        private Long id;
        private String eventoNombre;
        private String zonaNombre;
        private String fila;
        private Integer numero;
        private String codigoQr;
    }
}