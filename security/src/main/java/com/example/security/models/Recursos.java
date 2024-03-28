package com.example.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name = "recursos")
public class Recursos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRecurso;

    @Column
    private String icon;

    @Column
    private String nameFront;

    @Column
    private String colorFront;

    @Column
    private String UrlFront;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfil_item_id")
    @ToString.Exclude
    @JsonIgnore
    private PerfilesItems perfilItem;

    @ToString.Exclude
    @OneToMany(mappedBy = "recurso", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<permission> permisos = new ArrayList<>();

    @ToString.Exclude
    @OneToMany(mappedBy = "recursos")
    @JsonIgnore
    private Set<RecursosAsignados> recursosAsignados;
}
