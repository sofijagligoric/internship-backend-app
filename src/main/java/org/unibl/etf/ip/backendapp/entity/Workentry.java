package org.unibl.etf.ip.backendapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "workentry")
public class Workentry {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idWorkEntry", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "activity", nullable = false)
    private String activity;

    @Column(name = "weekStart", nullable = false)
    private LocalDate weekStart;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idInternship", nullable = false)
    private Internship internship;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idStudent", nullable = false)
    private Student student;

}