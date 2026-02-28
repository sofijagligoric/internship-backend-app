package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.backendapp.dto.ApplicationViewDTO;
import org.unibl.etf.ip.backendapp.dto.InternshipDTO;
import org.unibl.etf.ip.backendapp.service.InternshipService;

import java.util.List;

@RestController
@RequestMapping("/api/internship")
public class InternshipController {

    @Autowired
    private InternshipService internshipService;

    @GetMapping("/{id}")
    public ResponseEntity<InternshipDTO> getById(@PathVariable Integer id) {
        InternshipDTO internship = internshipService.getById(id);
        return ResponseEntity.ok(internship);
    }

    @GetMapping("/available")
    public ResponseEntity<List<InternshipDTO>> getAvailable() {
        List<InternshipDTO> internship = internshipService.getAllAvailable();
        return ResponseEntity.ok(internship);
    }

    @GetMapping
    public ResponseEntity<List<InternshipDTO>> getAll() {
        List<InternshipDTO> internships = internshipService.getAll();
        return ResponseEntity.ok(internships);
    }

    /*
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (internshipService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        try {
            if (internshipService.delete(id)) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (org.springframework.dao.DataIntegrityViolationException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @PostMapping
    public ResponseEntity<InternshipDTO> create(@RequestBody InternshipDTO internship) {
        InternshipDTO created = internshipService.create(internship);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<InternshipDTO>> getByCompanyId(@PathVariable Integer companyId) {
        List<InternshipDTO> internships = internshipService.getAllByCompanyId(companyId);
        return ResponseEntity.ok(internships);
    }

    @PutMapping("/{id}")
    public ResponseEntity<InternshipDTO> update(@PathVariable Integer id, @RequestBody InternshipDTO internshipDTO) {
        InternshipDTO updated = internshipService.update(id, internshipDTO);
        return ResponseEntity.ok(updated);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<InternshipDTO> changeStatus(@PathVariable Integer id) {
        InternshipDTO updated = internshipService.updateAvailability(id);
        return ResponseEntity.ok(updated);
    }

}
