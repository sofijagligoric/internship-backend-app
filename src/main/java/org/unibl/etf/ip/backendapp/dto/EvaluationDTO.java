package org.unibl.etf.ip.backendapp.dto;

import lombok.Data;

@Data
public class EvaluationDTO {
    private Integer id;
    private String description;
    private Integer studentId;
    private Integer internshipId;
    private Integer companyId;
}
