package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.backendapp.dto.ApplicationDTO;
import org.unibl.etf.ip.backendapp.service.ApplicationService;

import java.util.List;

@RestController
@RequestMapping("/api/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/{id}")
    public ResponseEntity<ApplicationDTO> getById(@PathVariable Integer id) {
        ApplicationDTO application = applicationService.getById(id);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<ApplicationDTO>> getAllForStudent(@PathVariable Integer studentId) {
        List<ApplicationDTO> applications = applicationService.getAllByStudentId(studentId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/student/status")
    public ResponseEntity<List<ApplicationDTO>> getAllForStudentWithStatus(@RequestParam Integer studentId, @RequestParam String status) {
        List<ApplicationDTO> applications = applicationService.getAllForStudentWithStatus(studentId, status);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<List<ApplicationDTO>> getAllForInternship(@PathVariable Integer internshipId) {
        List<ApplicationDTO> applications = applicationService.getAllByInternshipId(internshipId);
        return ResponseEntity.ok(applications);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<ApplicationDTO>> getAllForCompany(@PathVariable Integer companyId) {
        List<ApplicationDTO> applications = applicationService.getAllByCompanyId(companyId);
        return ResponseEntity.ok(applications);
    }

    @PostMapping
    public ResponseEntity<ApplicationDTO> create(@RequestParam Integer studentId, @RequestParam Integer internshipId) {
        ApplicationDTO created = applicationService.create(studentId, internshipId);
        return ResponseEntity.ok(created);
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationDTO> changeStatus(@PathVariable Integer id, @RequestParam String status) {
        ApplicationDTO updated = applicationService.changeStatus(id, status);
        return ResponseEntity.ok(updated);
    }
}
