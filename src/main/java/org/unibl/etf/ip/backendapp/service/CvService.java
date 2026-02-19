package org.unibl.etf.ip.backendapp.service;

import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.backendapp.dto.CvDTO;
import org.unibl.etf.ip.backendapp.entity.Cv;
import org.unibl.etf.ip.backendapp.entity.Student;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.CVRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CvService {

    private CVRepository cvRepository;
    private StudentRepository studentRepository;
    private ModelMapper modelMapper;
    private final FileStorageService fileStorageService;

    private final String imagesDir = "uploads/cvImages/";

    public CvService(CVRepository cvRepository, StudentRepository studentRepository, ModelMapper modelMapper, FileStorageService fileStorageService) {
        this.cvRepository = cvRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.fileStorageService = fileStorageService;
    }


    public CvDTO getById(Integer cvId) {
        Cv cv = cvRepository.findById(cvId).orElseThrow(() -> new ResourceNotFoundException("CV with id " + cvId + " not found"));
        return modelMapper.map(cv, CvDTO.class);
    }

    public CvDTO getByStudent(Integer studentId) {
        if (!studentRepository.existsById(studentId)) {
            throw new ResourceNotFoundException("Student not found");
        }
        Cv cv = cvRepository.findByStudentId(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("CV not found"));
        return modelMapper.map(cv, CvDTO.class);
    }

    public List<CvDTO> getAll() {
        return cvRepository.findAll().stream().map(cv -> modelMapper.map(cv, CvDTO.class)).collect(Collectors.toList());
    }

    public CvDTO create(CvDTO cvDTO, MultipartFile image) {
        if(cvRepository.existsByStudentId(cvDTO.getStudentId())) {
            throw new RuntimeException("This student already has CV.");
        }
        Student student = studentRepository.findById(cvDTO.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student does not exist."));
        Cv cv = modelMapper.map(cvDTO, Cv.class);
        cv.setStudent(student);
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.saveFile(image, imagesDir);
            cv.setImagePath(fileName);
        }
        Cv saved = cvRepository.save(cv);
        return modelMapper.map(saved, CvDTO.class);
    }

    public boolean delete(Integer cvId) {
        if(cvRepository.existsById(cvId)) {
            cvRepository.deleteById(cvId);
            return true;
        }
        return false;
    }

    public CvDTO update(Integer cvId, CvDTO cvDTO, MultipartFile image){
        Cv original = cvRepository.findById(cvId).orElseThrow(() -> new ResourceNotFoundException("CV not found"));
        if(cvDTO.getStudentId() != null && !original.getStudent().getId().equals(cvDTO.getStudentId()))
            throw new RuntimeException("Student id mismatch");
        if(cvDTO.getContact() != null)
            original.setContact(cvDTO.getContact());
        if(cvDTO.getExperience() != null)
            original.setExperience(cvDTO.getExperience());
        if(cvDTO.getDescription() != null)
            original.setDescription(cvDTO.getDescription());
        if(cvDTO.getInterests() != null)
            original.setInterests(cvDTO.getInterests());
        if(cvDTO.getFirstName() != null)
            original.setFirstName(cvDTO.getFirstName());
        if(cvDTO.getLastName()!= null)
            original.setLastName(cvDTO.getLastName());
        if(cvDTO.getSchool() != null)
            original.setSchool(cvDTO.getSchool());
        if(cvDTO.getInternships() != null)
            original.setInternships(cvDTO.getInternships());
        if(cvDTO.getSkills() != null)
            original.setSkills(cvDTO.getSkills());
        if(cvDTO.getTraining() != null)
            original.setTraining(cvDTO.getTraining());
        if(cvDTO.getImagePath() != null)
            original.setImagePath(cvDTO.getImagePath());
        if (image != null && !image.isEmpty()) {
            String fileName = fileStorageService.saveFile(image, imagesDir);
            original.setImagePath(fileName);
        }
        Cv saved = cvRepository.save(original);
        return modelMapper.map(saved, CvDTO.class);
    }
}
