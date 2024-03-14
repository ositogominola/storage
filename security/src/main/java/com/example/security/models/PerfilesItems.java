package com.example.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name = "PerfilesItems")
public class PerfilesItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPerfil;

    @Column(nullable = false)
    private String nombre;

    @Column
    private String icon;

    @Column
    private String urlFront;

    @ToString.Exclude
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "perfiles_id")
    private perfiles perfil;



    /**@ToString.Exclude
    @OneToMany(mappedBy = "perfilItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<permission> permissions = new ArrayList<>();**/

    @ToString.Exclude
    @OneToMany(mappedBy = "perfilItem", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Recursos> recursos = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "perfilesItems")
    private Set<user> users = new HashSet<>();

    @OneToMany(mappedBy = "perfilesItems")
    private Set<perfilesItemsAsignados> perfilesItemsAsignados;
}
