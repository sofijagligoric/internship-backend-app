package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.backendapp.dto.CreateCompanyDTO;
import org.unibl.etf.ip.backendapp.dto.CompanyDTO;
import org.unibl.etf.ip.backendapp.service.CompanyService;

import java.util.List;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    @Autowired
    private CompanyService companyService;

    @GetMapping("/{id}")
    public ResponseEntity<CompanyDTO> getById(@PathVariable Integer id) {
        CompanyDTO company = companyService.getCompanyById(id);
        return ResponseEntity.ok(company);
    }

    @GetMapping
    public ResponseEntity<List<CompanyDTO>> getAll() {
        List<CompanyDTO> companys = companyService.getAllCompanies();
        return ResponseEntity.ok(companys);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (companyService.deleteCompany(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<CompanyDTO> create(@RequestBody CreateCompanyDTO company) {
        CompanyDTO created = companyService.createCompany(company);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyDTO> update(
            @PathVariable Integer id,
            @RequestBody CompanyDTO companyDTO) {

        CompanyDTO updated = companyService.updateCompany(id, companyDTO);
        return ResponseEntity.ok(updated);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<Void> changeStatus(@PathVariable Integer id) {
        companyService.changeStatus(id);
        return ResponseEntity.ok().build();
    }
}
