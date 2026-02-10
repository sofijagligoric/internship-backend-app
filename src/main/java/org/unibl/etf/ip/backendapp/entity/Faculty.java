package org.unibl.etf.ip.backendapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "faculty")
public class Faculty {
    @Id
    @Column(name = "idUser", nullable = false)
    private Integer id;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idUser", nullable = false)
    private User user;

    @Column(name = "name", nullable = false, length = 500)
    private String name;

    /*
    @OneToMany(mappedBy = "idFaculty")
    private Set<Grade> grades = new LinkedHashSet<>();

     */

}