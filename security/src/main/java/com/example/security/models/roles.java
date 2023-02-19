package com.example.security.models;


import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "roles_permissions",
            joinColumns = @JoinColumn(name = "roles_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id"))
    Set<permission> permissions;

    public void addPermission(permission permissionsi){
        this.permissions.add(permissionsi);
    }

}
