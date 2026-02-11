package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationDTO {
    private Integer id;
    private String description;
    private Integer studentId;
    private Integer internshipId;
    private Integer companyId;
}
