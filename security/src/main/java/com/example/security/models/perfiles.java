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
@Table(name = "perfiles")
public class perfiles {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPerfil;

    @Column(nullable = false)
    private String nombre;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "perfil", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PerfilesItems> perfilItems = new ArrayList<>();

    @ToString.Exclude
    @JsonIgnore
    @ManyToMany(mappedBy = "perfiles", fetch = FetchType.LAZY)
    Set<roles> roles;
}
