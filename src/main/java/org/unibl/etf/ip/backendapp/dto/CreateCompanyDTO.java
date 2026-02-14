package org.unibl.etf.ip.backendapp.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCompanyDTO {
    private String username;
    private String password;
    private String name;
    private String email;
    private Boolean active;
    private String address;
}
