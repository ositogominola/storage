package com.example.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name = "roles")
public class roles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idRol;

    @Column(nullable = false)
    private String name;

    /*@Column(nullable = false)
    private String idUsuario;*/

    /*@Column(nullable = true)
    @OneToMany(mappedBy = "roles")
    private Set<user> user = new HashSet<>();*/


    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinTable(
            name = "roles_perfiles",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "perfiles_id"))
    Set<perfiles> perfiles;

    @JsonIgnore
    @OneToMany(mappedBy = "rol")
    private Set<PermissionAsignados> permisosAsignados;

    @JsonIgnore
    @OneToMany(mappedBy = "rol")
    private Set<perfilesItemsAsignados> perfilesItemsAsignados;

    @JsonIgnore
    @OneToMany(mappedBy = "rol")
    private Set<RecursosAsignados> recursosAsignados;

}
