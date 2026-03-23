package org.unibl.etf.ip.backendapp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.unibl.etf.ip.backendapp.util.Role;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", nullable = false)
    private Integer id;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false, length = 500)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}