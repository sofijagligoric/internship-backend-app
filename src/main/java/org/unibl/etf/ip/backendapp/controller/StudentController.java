package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.backendapp.dto.CreateStudentDTO;
import org.unibl.etf.ip.backendapp.dto.StudentDTO;
import org.unibl.etf.ip.backendapp.service.StudentService;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/student")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/{id}")
    public ResponseEntity<StudentDTO> getById(@PathVariable Integer id) {
        StudentDTO student = studentService.getStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping("/complete/{id}")
    public ResponseEntity<CreateStudentDTO> getByIdForUpdate(@PathVariable Integer id) {
        CreateStudentDTO student = studentService.getCompleteStudentById(id);
        return ResponseEntity.ok(student);
    }

    @GetMapping
    public ResponseEntity<List<StudentDTO>> getAll() {
        List<StudentDTO> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (studentService.deleteStudent(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<StudentDTO> create(@RequestBody CreateStudentDTO student) {
        StudentDTO created = studentService.createStudent(student);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StudentDTO> update(@PathVariable Integer id, @RequestBody CreateStudentDTO studentDTO) {
        StudentDTO updated = studentService.updateStudent(id, studentDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/faculty/{facultyId}")
    public ResponseEntity<List<StudentDTO>> getByFacultyId(@PathVariable Integer facultyId) {
        List<StudentDTO> students = studentService.getStudentsByFacultyId(facultyId);
        return ResponseEntity.ok(students);
    }
    @PostMapping("/csvUpload/{facultyId}")
    public ResponseEntity<?> uploadCSVFile(@PathVariable Integer facultyId,@RequestBody byte[] fileBytes) {
        boolean success = studentService.importFromCsv(new ByteArrayInputStream(fileBytes), facultyId);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    /*
    @PostMapping("/csvUpload/{facultyId}")
    public ResponseEntity<?> uploadCSVFile(
            @PathVariable Integer facultyId,
            @RequestParam("file") MultipartFile file) {
        boolean success = studentService.importFromCsv(file, facultyId);
        if (success) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

     */
}
