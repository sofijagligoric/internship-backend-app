package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.ApplicationDTO;
import org.unibl.etf.ip.backendapp.dto.CvDTO;
import org.unibl.etf.ip.backendapp.entity.Application;
import org.unibl.etf.ip.backendapp.entity.Cv;
import org.unibl.etf.ip.backendapp.entity.Internship;
import org.unibl.etf.ip.backendapp.entity.Student;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.ApplicationRepository;
import org.unibl.etf.ip.backendapp.repository.InternshipRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ApplicationService {

    private ApplicationRepository applicationRepository;
    private InternshipRepository internshipRepository;
    private StudentRepository studentRepository;
    private ModelMapper modelMapper;

    public ApplicationService(ApplicationRepository applicationRepository, InternshipRepository internshipRepository
    , StudentRepository studentRepository, ModelMapper modelMapper)
    {
        this.applicationRepository = applicationRepository;
        this.internshipRepository = internshipRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
    }

    public ApplicationDTO create(Integer studentId, Integer internshipId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student does not exist."));
        Internship internship = internshipRepository.findById(internshipId)
                .orElseThrow(() -> new ResourceNotFoundException("Internship does not exist."));
        if(applicationRepository.existsByStudentIdAndInternshipId(studentId, internshipId))
            throw new RuntimeException("Application already exists.");

        Application application = new Application();
        application.setStudent(student);
        application.setInternship(internship);
        application.setStatus("PENDING");
        application.setDate(LocalDate.now());
        Application saved = applicationRepository.save(application);
        return modelMapper.map(saved, ApplicationDTO.class);
    }

    public ApplicationDTO changeStatus(Integer applicationId, String status) {
        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Application does not exist."));
        application.setStatus(status);
        Application saved = applicationRepository.save(application);
        return modelMapper.map(saved, ApplicationDTO.class);
    }

    public List<ApplicationDTO> getAllByStudentId(Integer studentId) {
        return applicationRepository.findByStudentId(studentId).stream().map(application -> modelMapper.map(application, ApplicationDTO.class)).collect(Collectors.toList());
    }

    public List<ApplicationDTO> getAllByInternshipId(Integer internshipId) {
        return applicationRepository.findByInternshipId(internshipId).stream().map(application -> modelMapper.map(application, ApplicationDTO.class)).collect(Collectors.toList());
    }

    public ApplicationDTO getById(Integer applicationId) {
        Application app = applicationRepository.findById(applicationId).orElseThrow(() -> new ResourceNotFoundException("Application with id " + applicationId + " not found"));
        return modelMapper.map(app, ApplicationDTO.class);
    }

    public List<ApplicationDTO> getAllByCompanyId(Integer companyId) {
        return applicationRepository.findAllByInternshipCompanyId(companyId).stream().map(application -> modelMapper.map(application, ApplicationDTO.class)).collect(Collectors.toList());
    }

    public List<ApplicationDTO> getAllForStudentWithStatus(Integer studentId, String status)
    {
        return applicationRepository.findAllByStudentIdAndStatus(studentId, status).stream().map(application -> modelMapper.map(application, ApplicationDTO.class)).collect(Collectors.toList());
    }
}
