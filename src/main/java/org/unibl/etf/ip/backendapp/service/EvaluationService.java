package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.CompanyDTO;
import org.unibl.etf.ip.backendapp.dto.EvaluationDTO;
import org.unibl.etf.ip.backendapp.entity.Company;
import org.unibl.etf.ip.backendapp.entity.Evaluation;
import org.unibl.etf.ip.backendapp.entity.Internship;
import org.unibl.etf.ip.backendapp.entity.Student;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.CompanyRepository;
import org.unibl.etf.ip.backendapp.repository.EvaluationRepository;
import org.unibl.etf.ip.backendapp.repository.InternshipRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class EvaluationService {
    private EvaluationRepository evaluationRepository;
    private StudentRepository studentRepository;
    private ModelMapper modelMapper;
    private CompanyRepository companyRepository;
    private InternshipRepository internshipRepository;

    public EvaluationService(EvaluationRepository evaluationRepository, InternshipRepository internshipRepository, StudentRepository studentRepository, ModelMapper modelMapper, CompanyRepository facadeRepository) {
        this.evaluationRepository = evaluationRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.internshipRepository = internshipRepository;
        this.companyRepository = facadeRepository;
    }

    public EvaluationDTO create(EvaluationDTO evaluationDTO) {
        Student student = studentRepository.findById(evaluationDTO.getStudentId())
                .orElseThrow(() -> new ResourceNotFoundException("Student does not exist."));
        Company company = companyRepository.findById(evaluationDTO.getCompanyId())
                    .orElseThrow(() -> new ResourceNotFoundException("Company does not exist."));
        Internship internship = internshipRepository.findById(evaluationDTO.getInternshipId())
                .orElseThrow(() -> new ResourceNotFoundException("Internship does not exist."));

        Evaluation evaluation = modelMapper.map(evaluationDTO, Evaluation.class);
        evaluation.setStudent(student);
        evaluation.setCompany(company);
        evaluation.setInternship(internship);
        Evaluation saved = evaluationRepository.save(evaluation);
        return modelMapper.map(saved, EvaluationDTO.class);
    }


    public boolean delete(int evaluationId) {
        if(evaluationRepository.existsById(evaluationId)) {
            evaluationRepository.deleteById(evaluationId);
            return true;
        }
        return false;
    }

    public List<EvaluationDTO> getForStudent(Integer studentId) {
        return evaluationRepository.findByStudentId(studentId).stream().map(evaluation -> modelMapper.map(evaluation, EvaluationDTO.class)).collect(Collectors.toList());
    }

    public List<EvaluationDTO> getForCompany(Integer companyId) {
        return evaluationRepository.findByCompanyId(companyId).stream().map(evaluation -> modelMapper.map(evaluation, EvaluationDTO.class)).collect(Collectors.toList());
    }

    public List<EvaluationDTO> getForInternship(Integer internshipId) {
        return evaluationRepository.findByInternshipId(internshipId).stream().map(evaluation -> modelMapper.map(evaluation, EvaluationDTO.class)).collect(Collectors.toList());
    }

    public List<EvaluationDTO> getForStudentAndCompany(Integer studentId, Integer companyId) {
        return evaluationRepository.findByStudentIdAndCompanyId(studentId, companyId).stream().map(evaluation -> modelMapper.map(evaluation, EvaluationDTO.class)).collect(Collectors.toList());
    }

    public EvaluationDTO getById(int evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new ResourceNotFoundException("Evaluation with id " + evaluationId + " not found"));
        return modelMapper.map(evaluation, EvaluationDTO.class);
    }
}
