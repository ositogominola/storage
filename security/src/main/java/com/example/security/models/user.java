package com.example.security.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.collection.spi.PersistentSet;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@ToString
@Entity
@Table(name = "user")
public class user {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idUser;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastname;

    @Column(nullable = false, unique = true)

    private String username;

    @Column(nullable = false)
    private Integer age;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Boolean enabled;

    @ManyToOne
    private roles roles;

    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private Set<factory> factorys= new HashSet<>();;

    @Column(nullable = false)
    private int subscription;

    public boolean is_valid(){
        if (this.name==null || this.lastname==null || this.username==null || this.email==null || this.password==null || this.age==null ||this.name=="" || this.lastname=="" || this.username=="" || this.email=="" || this.password=="" ||  this.age<=0){
            return false;
        }
        else {
            return true;
        }
    }

}
