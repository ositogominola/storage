package com.example.security.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.UUID;
import com.fasterxml.uuid.Generators;
import lombok.*;

@Setter
@Getter
@ToString(exclude = "usuario")
@Entity
@Table(name = "factory")
public class factory {

    @Id
    @GeneratedValue(generator = "uuid")
    private UUID id;

    @Column()
    private String nombre;

    @Column()
    private String logo;

    @Column()
    private String direccion;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    user usuario;

    public factory() {
        this.id = Generators.timeBasedGenerator().generate();
    }
}
