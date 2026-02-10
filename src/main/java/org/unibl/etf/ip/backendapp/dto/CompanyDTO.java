package org.unibl.etf.ip.backendapp.dto;

import lombok.Data;

@Data
public class CompanyDTO {
    private Integer id;
    private String name;
    private String email;
    private Boolean active;
    private String address;
}
