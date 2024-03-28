package com.example.security.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Entity
@Table(name = "permissionAsignados")
public class PermissionAsignados {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPermissionAsign;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "roles_id")
    private roles rol;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "permission_id")
    private permission permiso;


}
