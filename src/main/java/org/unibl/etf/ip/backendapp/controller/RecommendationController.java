package org.unibl.etf.ip.backendapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.unibl.etf.ip.backendapp.dto.RecommendationDTO;
import org.unibl.etf.ip.backendapp.service.GeminiService;
import org.unibl.etf.ip.backendapp.service.RecommendationService;

import java.util.List;

@RestController
@RequestMapping("/api/recommendation")
public class RecommendationController {

    @Autowired
    private RecommendationService recommendationService;
   /*
    @Autowired
    private GeminiService geminiService;

    */

    @GetMapping("/{studentId}")
    public ResponseEntity<List<RecommendationDTO>> getRecommendation(@PathVariable("studentId") Integer studentId) {
        List<RecommendationDTO> recommendations = recommendationService.recommendForStudent(studentId);
        return ResponseEntity.ok(recommendations);
    }

    /*
    @GetMapping("/pitaj")
    public String ask(@RequestParam String pitanje) {
        return geminiService.generateAnswer(pitanje);
    }*/
}
