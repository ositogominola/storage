package com.example.security.models;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.uuid.Generators;
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
    @GeneratedValue(generator = "uuid")
    private UUID idUser;

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

    @JsonIgnore
    @Column(nullable = false)
    private String password;

    @JsonIgnore
    @Column(nullable = false)
    private Boolean enabled;


    @ManyToOne
    private roles roles;

    @Column(nullable = false)
    private int subscription;

    @JsonIgnore
    @OneToMany(mappedBy = "usuario",fetch = FetchType.LAZY, cascade = CascadeType.ALL )
    private Set<factory> factorys= new HashSet<>();

    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_perfilesitems",
            joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "idUser"),
            inverseJoinColumns = @JoinColumn(name = "perfilesitems_id", referencedColumnName = "idPerfil"))
    private Set<PerfilesItems> perfilesItems = new HashSet<>();

    public void addPerfileItems(PerfilesItems pri){
        this.perfilesItems.add(pri);
    }

    public user(){
        this.idUser=Generators.timeBasedGenerator().generate();
    }

    public boolean is_valid(){
        if (this.name==null || this.lastname==null || this.username==null || this.email==null || this.password==null || this.age==null ||this.name=="" || this.lastname=="" || this.username=="" || this.email=="" || this.password=="" ||  this.age<=0){
            return false;
        }
        else {
            return true;
        }
    }

}
