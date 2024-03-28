package com.example.security.models;

import com.fasterxml.jackson.annotation.*;
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

    @Column(nullable = true)
    private Integer grupoPermiso;

    @Column
    private Boolean urlIgnore;

    @Column
    private String nombre;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recurso_id")
    private Recursos recurso;

    @JsonIgnore
    @OneToMany(mappedBy = "permiso")
    private Set<PermissionAsignados> permisosAsignados;

    // Método getter personalizado para grupoPermiso
    @Column
    public int getGrupoPermiso() {
        return grupoPermiso != null ? grupoPermiso : -1;
    }

    @JsonProperty("recursoId")
    public int getRecursoId() {
        return recurso != null ? recurso.getIdRecurso() : -1;
    }
}
