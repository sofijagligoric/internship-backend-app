package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.backendapp.dto.GradeDTO;
import org.unibl.etf.ip.backendapp.dto.InternshipDTO;
import org.unibl.etf.ip.backendapp.service.GradeService;

import java.util.List;

@RestController
@RequestMapping("/api/grade")
public class GradeController {
    
    @Autowired
    private GradeService gradeService;

    @GetMapping("/{id}")
    public ResponseEntity<GradeDTO> getById(@PathVariable Integer id) {
        GradeDTO grade = gradeService.getById(id);
        return ResponseEntity.ok(grade);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<GradeDTO>> getByStudentId(@PathVariable Integer studentId) {
        List<GradeDTO> grade = gradeService.getForStudent(studentId);
        return ResponseEntity.ok(grade);
    }

    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<GradeDTO>> getByFacultyId(@PathVariable Integer facultyId) {
        List<GradeDTO> grade = gradeService.getForFaculty(facultyId);
        return ResponseEntity.ok(grade);
    }

    @GetMapping
    public ResponseEntity<List<GradeDTO>> getAll() {
        List<GradeDTO> grades = gradeService.getAll();
        return ResponseEntity.ok(grades);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (gradeService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<GradeDTO> create(@RequestBody GradeDTO grade) {
        GradeDTO created = gradeService.create(grade);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GradeDTO> update(@PathVariable Integer id, @RequestBody GradeDTO gradeDTO) {
        GradeDTO updated = gradeService.update(id, gradeDTO);
        return ResponseEntity.ok(updated);
    }
}
