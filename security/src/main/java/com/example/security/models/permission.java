package com.example.security.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name = "permission")
public class permission {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermission;

    @Column(nullable = false)
    private String url;

    @Column(nullable = false)
    private String method;

    @Column
    private Boolean pertRecurso;

    @Column
    private Boolean permisoRequerido;

    @Column
    private int grupoPermiso;

    @Column
    private Boolean urlIgnore;

    @Column
    private String nombre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurso_id")
    @ToString.Exclude
    @JsonIgnore
    private Recursos recurso;

    @OneToMany(mappedBy = "permiso")
    private Set<PermissionAsignados> permisosAsignados;
}
