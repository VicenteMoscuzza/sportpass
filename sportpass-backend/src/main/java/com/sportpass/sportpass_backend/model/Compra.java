package com.sportpass.sportpass_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
@Table(name = "compras")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false)
    private LocalDateTime fecha;

    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    private Estado estado;

    private String mercadoPagoId;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<Entrada> entradas;

    @PrePersist
    public void prePersist() {
        fecha = LocalDateTime.now();
    }

    public enum Estado {
        PENDIENTE, APROBADA, RECHAZADA
    }
}