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
    @JoinColumn(name = "roles_id")
    private roles rol;

    @ManyToOne
    @JoinColumn(name = "permission_id")
    private permission permiso;


}
