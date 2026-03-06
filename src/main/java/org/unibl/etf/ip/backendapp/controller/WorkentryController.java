package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.backendapp.dto.WorkentryDTO;
import org.unibl.etf.ip.backendapp.dto.WorkentryViewDTO;
import org.unibl.etf.ip.backendapp.service.WorkentryService;

import java.util.List;

@RestController
@RequestMapping("/api/workentry")
public class WorkentryController {

    @Autowired
    private WorkentryService workentryService;

    @GetMapping("/{id}")
    public ResponseEntity<WorkentryDTO> getById(@PathVariable Integer id) {
        WorkentryDTO workentry = workentryService.getById(id);
        return ResponseEntity.ok(workentry);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<WorkentryDTO>> getByStudentId(@PathVariable Integer studentId) {
        List<WorkentryDTO> workentry = workentryService.getAllForStudent(studentId);
        return ResponseEntity.ok(workentry);
    }

    @GetMapping("/student/{studentId}/all")
    public ResponseEntity<List<WorkentryViewDTO>> getAllByStudentId(@PathVariable Integer studentId) {
        List<WorkentryViewDTO> workentry = workentryService.getAllForStudentView(studentId);
        return ResponseEntity.ok(workentry);
    }

    @GetMapping("/internship/{internshipId}")
    public ResponseEntity<List<WorkentryDTO>> getByInternshipId(@PathVariable Integer internshipId) {
        List<WorkentryDTO> workentry = workentryService.getAllForInternship(internshipId);
        return ResponseEntity.ok(workentry);
    }

    @GetMapping("/company/{companyId}/internship/{internshipId}/student/{studentId}")
    public ResponseEntity<List<WorkentryDTO>> getByCompanyInternshipAndStudent(
            @PathVariable Integer companyId,
            @PathVariable Integer internshipId,
            @PathVariable Integer studentId) {
        List<WorkentryDTO> workentries = workentryService.getAllForStudentAndInternship(companyId, studentId, internshipId);
        return ResponseEntity.ok(workentries);
    }

    @GetMapping
    public ResponseEntity<List<WorkentryDTO>> getAll() {
        List<WorkentryDTO> workentrys = workentryService.getAll();
        return ResponseEntity.ok(workentrys);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (workentryService.deleteWorkEntry(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<WorkentryDTO> create(@RequestBody WorkentryDTO workentry) {
        WorkentryDTO created = workentryService.create(workentry);
        return ResponseEntity.ok(created);
    }
}
