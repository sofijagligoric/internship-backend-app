package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateStudentDTO {
    private String firstName;
    private String lastName;
    private String indeks;
    private String email;
    private Integer facultyId;
    private String username;
    private String password;
}
