package org.unibl.etf.ip.backendapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "student")
public class Student {
    @Id
    @Column(name = "idUser", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Column(name = "indeks", nullable = false, length = 45)
    private String indeks;

    @Column(name = "email", nullable = false)
    private String email;

    /*
    @OneToMany(mappedBy = "idStudent")
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idStudent")
    private Set<Cv> cvs = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idStudent")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idStudent")
    private Set<Grade> grades = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idStudent")
    private Set<Workentry> workentries = new LinkedHashSet<>();

     */

}