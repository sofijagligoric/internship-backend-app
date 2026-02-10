package org.unibl.etf.ip.backendapp.dto;

import lombok.Data;

@Data
public class StudentDTO {
    private Integer id;
    private String firstName;
    private String lastName;
    private String indeks;
    private String email;
}
