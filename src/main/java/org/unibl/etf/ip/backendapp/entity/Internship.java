package org.unibl.etf.ip.backendapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "internship")
public class Internship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idInternship", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "startDate", nullable = false)
    private LocalDate startDate;

    @Column(name = "endDate", nullable = false)
    private LocalDate endDate;

    @Column(name = "available", nullable = false)
    private Boolean available = false;

    @Lob
    @Column(name = "about", nullable = false)
    private String about;

    @Lob
    @Column(name = "technologies", nullable = false)
    private String technologies;

    @Lob
    @Column(name = "skills", nullable = false)
    private String skills;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idCompany", nullable = false)
    private Company company;

    @Lob
    @Column(name = "requirements", nullable = false)
    private String requirements;

    /*
    @OneToMany(mappedBy = "idInternship")
    private Set<Application> applications = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idInternship")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idInternship")
    private Set<Grade> grades = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idInternship")
    private Set<Workentry> workentries = new LinkedHashSet<>();

     */

}