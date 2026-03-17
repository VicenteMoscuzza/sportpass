package com.sportpass.sportpass_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "zonas")
public class Zona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre; // VIP, Platea, Popular

    private Integer capacidad;

    @ManyToOne
    @JoinColumn(name = "estadio_id", nullable = false)
    private Estadio estadio;

    @OneToMany(mappedBy = "zona", cascade = CascadeType.ALL)
    private List<Asiento> asientos;
}