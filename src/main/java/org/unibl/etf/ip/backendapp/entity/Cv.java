package org.unibl.etf.ip.backendapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "cv")
public class Cv {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idCV", nullable = false)
    private Integer id;

    @Lob
    @Column(name = "description")
    private String description;

    @Column(name = "imagePath", length = 500)
    private String imagePath;

    @Lob
    @Column(name = "school", nullable = false)
    private String school;

    @Lob
    @Column(name = "internships")
    private String internships;

    @Lob
    @Column(name = "experience")
    private String experience;

    @Column(name = "contact", nullable = false)
    private String contact;

    @Lob
    @Column(name = "interests", nullable = false)
    private String interests;

    @Lob
    @Column(name = "skills", nullable = false)
    private String skills;

    @Column(name = "firstName", nullable = false)
    private String firstName;

    @Column(name = "lastName", nullable = false)
    private String lastName;

    @Lob
    @Column(name = "training")
    private String training;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "idStudent", nullable = false)
    private Student student;

}