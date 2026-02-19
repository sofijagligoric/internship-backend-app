package org.unibl.etf.ip.backendapp.service;

import org.springframework.stereotype.Service;
import org.unibl.etf.ip.backendapp.dto.RecommendationDTO;
import org.unibl.etf.ip.backendapp.dto.RecommendationViewDTO;
import org.unibl.etf.ip.backendapp.entity.Cv;
import org.unibl.etf.ip.backendapp.entity.Internship;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.CVRepository;
import org.unibl.etf.ip.backendapp.repository.InternshipRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

@Service
public class RecommendationService {

    private InternshipRepository internshipRepository;
    private CVRepository cvRepository;
    private GeminiService geminiService;
    private ObjectMapper objectMapper = new ObjectMapper();

    public RecommendationService(InternshipRepository internshipRepository, CVRepository cvRepository, GeminiService geminiService) {
        this.internshipRepository = internshipRepository;
        this.cvRepository = cvRepository;
        this.geminiService = geminiService;
    }

    public List<RecommendationViewDTO> recommendForStudent(Integer studentId) {
        Cv cv = cvRepository.findByStudentId(studentId).orElseThrow(() -> new ResourceNotFoundException("CV not found"));
        List<Internship> internships = internshipRepository.findByAvailableTrue();
        if (internships.isEmpty()) {
            return List.of();
        }
        String cvText = createCVPromptText(cv);
        String internshipsText = createInternshipsPromptText(internships);
        String prompt = createFullPrompt(cvText, internshipsText);
        String aiResponse = geminiService.generateAnswer(prompt);
        List<RecommendationDTO> aiRecommendations = parseAIResponse(aiResponse);
        return aiRecommendations.stream()
                .map(aiRec -> {
                    Internship internship = internshipRepository.findById(aiRec.getInternshipId()).orElse(null);
                    if (internship == null) {
                        return null;
                    }
                    return new RecommendationViewDTO(
                            internship.getId(),
                            internship.getName(),
                            internship.getAbout(),
                            internship.getTechnologies(),
                            internship.getSkills(),
                            internship.getRequirements(),
                            aiRec.getScore(),
                            aiRec.getReason()
                    );
                })
                .filter(r -> r != null)
                .sorted((a, b) -> Double.compare(b.getScore(), a.getScore()))
                .limit(5)
                .toList();
    }

    private String createCVPromptText(Cv cv) {
        return """
                Student CV:
                First Name: %s
                Last Name: %s
                Description: %s
                School: %s
                Skills: %s
                Interests: %s
                Experience: %s
                Internships: %s
                Training: %s
                """.formatted(
                cv.getFirstName(),
                cv.getLastName(),
                cv.getDescription() != null ? cv.getDescription() : "",
                cv.getSchool() != null ? cv.getSchool() : "",
                cv.getSkills() != null ? cv.getSkills() : "",
                cv.getInterests() != null ? cv.getInterests() : "",
                cv.getExperience() != null ? cv.getExperience() : "",
                cv.getInternships() != null ? cv.getInternships() : "",
                cv.getTraining() != null ? cv.getTraining() : ""
        );
    }

    private String createInternshipsPromptText(List<Internship> internships) {
        StringBuilder internshipText = new StringBuilder();
        for (Internship internship : internships) {
            internshipText.append("""
                    
                    Internship ID: %d
                    Name: %s
                    About: %s
                    Technologies: %s
                    Skills: %s
                    Requirements: %s
                    ------------------------
                    """.formatted(
                    internship.getId(),
                    internship.getName(),
                    internship.getAbout(),
                    internship.getTechnologies(),
                    internship.getSkills(),
                    internship.getRequirements()
            ));
        }
        return internshipText.toString();
    }

    private String createFullPrompt(String cvText, String internshipsText) {

        return """
                You are an AI system that evaluates internship compatibility.
                
                Based on the student CV and the list of internships,
                return ONLY a valid JSON array in this exact format:
                
                [
                  {
                    "internshipId": number,
                    "score": number between 0 and 1,
                    "reason": "short explanation"
                  }
                ]
                
                Return results for ALL internships.
                Do not return anything except valid JSON.
                
                %s
                
                Internships:
                %s
                """.formatted(cvText, internshipsText);
    }

    private List<RecommendationDTO> parseAIResponse(String response) {
        try {
            response = response.replace("```json", "").replace("```", "").trim();
            return objectMapper.readValue(response, new TypeReference<List<RecommendationDTO>>() {
            });
        } catch (Exception e) {
            return List.of();
        }
    }
}
