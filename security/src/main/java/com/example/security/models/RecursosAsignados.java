package com.example.security.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "RecursosAsignados")
public class RecursosAsignados {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPerfilItemAsign;

    @ManyToOne
    @JoinColumn(name = "roles_id")
    private roles rol;

    @ManyToOne
    @JoinColumn(name = "recursos_id")
    private Recursos recursos;
}
