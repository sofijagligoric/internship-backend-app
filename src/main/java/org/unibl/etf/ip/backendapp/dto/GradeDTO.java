package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeDTO {
    private Integer id;
    private Integer value;
    private Integer studentId;
    private Integer internshipId;
    private Integer facultyId;
}
