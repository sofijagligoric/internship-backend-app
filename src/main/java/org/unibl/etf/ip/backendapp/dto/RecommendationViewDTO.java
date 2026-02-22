package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationViewDTO {

    private Integer internshipId;
    private String internshipName;
    private String about;
    private String technologies;
    private String skills;
    private String requirements;
    private String company;

    private Double score;
    private String reason;
}