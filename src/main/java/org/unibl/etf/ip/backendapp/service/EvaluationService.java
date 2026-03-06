package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.EvaluationDTO;
import org.unibl.etf.ip.backendapp.dto.EvaluationViewDTO;
import org.unibl.etf.ip.backendapp.entity.*;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.CompanyRepository;
import org.unibl.etf.ip.backendapp.repository.EvaluationRepository;
import org.unibl.etf.ip.backendapp.repository.InternshipRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;

import java.util.List;
import java.util.Optional;
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
        if(evaluationRepository.existsByStudentIdAndInternshipId(evaluationDTO.getStudentId(), evaluationDTO.getInternshipId())) {
            throw new RuntimeException("Evaluation for this student and this internship already exists.");
        }
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

    public List<EvaluationViewDTO> getAllForStudent(Integer studentId) {
        return evaluationRepository.findByStudentId(studentId).stream().map(evaluation -> modelMapper.map(evaluation, EvaluationViewDTO.class)).collect(Collectors.toList());
    }

    public List<EvaluationDTO> getForCompany(Integer companyId) {
        return evaluationRepository.findByCompanyId(companyId).stream().map(evaluation -> modelMapper.map(evaluation, EvaluationDTO.class)).collect(Collectors.toList());
    }

    public EvaluationDTO getById(int evaluationId) {
        Evaluation evaluation = evaluationRepository.findById(evaluationId).orElseThrow(() -> new ResourceNotFoundException("Evaluation with id " + evaluationId + " not found"));
        return modelMapper.map(evaluation, EvaluationDTO.class);
    }

    public EvaluationDTO getForStudentAndInternship(int companyId, int studentId, int internshipId) {
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Internship not found"));
        if (!internship.getCompany().getId().equals(companyId)) {
            throw new ResourceNotFoundException("This internship does not belong to the specified company.");
        }
        Optional<Evaluation> evaluationOpt = evaluationRepository.findByStudentIdAndInternshipId(studentId, internshipId);
        if (evaluationOpt.isPresent()) {
            return modelMapper.map(evaluationOpt.get(), EvaluationDTO.class);
        } else {
            return null;
        }
    }

    public List<EvaluationDTO> getAll()
    {
        return evaluationRepository.findAll().stream().map(evaluation -> modelMapper.map(evaluation, EvaluationDTO.class)).collect(Collectors.toList());
    }

    public EvaluationDTO update(Integer id, EvaluationDTO evaluationDTO) {
        Evaluation evaluation = evaluationRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Evaluation does not exist."));
        if(evaluationDTO.getDescription() != null)
            evaluation.setDescription(evaluationDTO.getDescription());
        if(evaluationDTO.getInternshipId() != null)
            evaluation.setInternship(internshipRepository.findById(evaluationDTO.getInternshipId()).orElseThrow(() -> new ResourceNotFoundException("Internship does not exist.")));
        Evaluation saved = evaluationRepository.save(evaluation);
        return modelMapper.map(saved, EvaluationDTO.class);

    }
}
