package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.backendapp.dto.RecommendationDTO;
import org.unibl.etf.ip.backendapp.dto.RecommendationViewDTO;
import org.unibl.etf.ip.backendapp.service.GeminiService;
import org.unibl.etf.ip.backendapp.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;

    @GetMapping("/{studentId}")
    public ResponseEntity<List<RecommendationViewDTO>> getRecommendation(@PathVariable("studentId") Integer studentId) {
        List<RecommendationViewDTO> recommendations = recommendationService.recommendForStudent(studentId);
        return ResponseEntity.ok(recommendations);
    }

}
