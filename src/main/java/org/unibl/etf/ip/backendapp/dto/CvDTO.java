package org.unibl.etf.ip.backendapp.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CvDTO {
    private Integer id;
    private String description;
    private String imagePath;
    private String school;
    private String internships;
    private String experience;
    private String contact;
    private String interests;
    private String skills;
    private String firstName;
    private String lastName;
    private String training;
    private Integer studentId;
}
