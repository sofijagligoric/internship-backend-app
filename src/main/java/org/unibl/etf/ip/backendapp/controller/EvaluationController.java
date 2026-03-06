package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.backendapp.dto.EvaluationDTO;
import org.unibl.etf.ip.backendapp.dto.EvaluationViewDTO;
import org.unibl.etf.ip.backendapp.dto.GradeDTO;
import org.unibl.etf.ip.backendapp.dto.WorkentryDTO;
import org.unibl.etf.ip.backendapp.service.EvaluationService;

import java.util.List;

@RestController
@RequestMapping("/api/evaluation")
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @GetMapping
    public ResponseEntity<List<EvaluationDTO>> getAll() {
        List<EvaluationDTO> evaluations = evaluationService.getAll();
        return ResponseEntity.ok(evaluations);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EvaluationDTO> getById(@PathVariable Integer id) {
        EvaluationDTO evaluation = evaluationService.getById(id);
        return ResponseEntity.ok(evaluation);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        if (evaluationService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<EvaluationDTO> create(@RequestBody EvaluationDTO evaluation) {
        EvaluationDTO created = evaluationService.create(evaluation);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EvaluationDTO>> getByStudentId(@PathVariable Integer studentId) {
        List<EvaluationDTO> evaluation = evaluationService.getForStudent(studentId);
        return ResponseEntity.ok(evaluation);
    }

    @GetMapping("/student/{studentId}/all")
    public ResponseEntity<List<EvaluationViewDTO>> getByAllStudentId(@PathVariable Integer studentId) {
        List<EvaluationViewDTO> evaluation = evaluationService.getAllForStudent(studentId);
        return ResponseEntity.ok(evaluation);
    }

    @GetMapping("/company/{companyId}")
    public ResponseEntity<List<EvaluationDTO>> getByCompanyId(@PathVariable Integer companyId) {
        List<EvaluationDTO> evaluation = evaluationService.getForCompany(companyId);
        return ResponseEntity.ok(evaluation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EvaluationDTO> update(@PathVariable Integer id, @RequestBody EvaluationDTO evaluationDTO) {
        EvaluationDTO updated = evaluationService.update(id, evaluationDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/company/{companyId}/internship/{internshipId}/student/{studentId}")
    public ResponseEntity<EvaluationDTO> getByStudentIdAndInternshipId(@PathVariable Integer companyId, @PathVariable Integer internshipId, @PathVariable Integer studentId) {
        EvaluationDTO workentry = evaluationService.getForStudentAndInternship(companyId, studentId, internshipId);
        if (workentry == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(workentry);
    }
}
