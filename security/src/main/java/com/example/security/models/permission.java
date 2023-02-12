package com.example.security.models;

import jakarta.persistence.*;

import lombok.Data;

import java.util.Set;

@Data
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


}
