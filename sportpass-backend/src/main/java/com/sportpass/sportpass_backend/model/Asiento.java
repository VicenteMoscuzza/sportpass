package com.sportpass.sportpass_backend.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "asientos")
public class Asiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String fila;

    @Column(nullable = false)
    private Integer numero;

    @ManyToOne
    @JoinColumn(name = "zona_id", nullable = false)
    private Zona zona;
}