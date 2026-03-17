package com.sportpass.sportpass_backend.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "estadios")
public class Estadio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String direccion;

    @Column(nullable = false)
    private String ciudad;

    private Integer capacidadTotal;

    @OneToMany(mappedBy = "estadio", cascade = CascadeType.ALL)
    private List<Zona> zonas;
}