package org.unibl.etf.ip.backendapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "company")
public class Company {
    @Id
    @Column(name = "idUser", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "active", nullable = false)
    private Boolean active = false;

    @Column(name = "address", nullable = false, length = 500)
    private String address;

    /*
    @OneToMany(mappedBy = "idCompany")
    private Set<Evaluation> evaluations = new LinkedHashSet<>();

    @OneToMany(mappedBy = "idCompany")
    private Set<Internship> internships = new LinkedHashSet<>();

     */

}