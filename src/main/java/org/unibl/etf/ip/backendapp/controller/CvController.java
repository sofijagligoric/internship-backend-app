package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.backendapp.dto.CvDTO;
import org.unibl.etf.ip.backendapp.service.CvService;
//import tools.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@RestController
@RequestMapping("/api/cv")
public class CvController {

    @Autowired
    private CvService cvService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CvDTO> create(
            @RequestPart("cv") String cvJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CvDTO cvDTO = mapper.readValue(cvJson, CvDTO.class);
        CvDTO created = cvService.create(cvDTO, image);
        return ResponseEntity.ok(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CvDTO> update(
            @PathVariable Integer id,
            @RequestPart("cv") String cvJson,
            @RequestPart(value = "image", required = false) MultipartFile image) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        CvDTO cvDTO = mapper.readValue(cvJson, CvDTO.class);
        CvDTO updated = cvService.update(id, cvDTO, image);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CvDTO> getById(@PathVariable Integer id) {
        CvDTO cv = cvService.getById(id);
        return ResponseEntity.ok(cv);
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<CvDTO> getByStudentId(@PathVariable Integer studentId) {
        CvDTO cv = cvService.getByStudent(studentId);
        return ResponseEntity.ok(cv);
    }

    @GetMapping
    public ResponseEntity<List<CvDTO>> getAll() {
        List<CvDTO> cvs = cvService.getAll();
        return ResponseEntity.ok(cvs);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        if (cvService.delete(id)) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

}
