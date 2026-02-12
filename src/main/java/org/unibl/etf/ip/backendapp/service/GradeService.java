package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.EvaluationDTO;
import org.unibl.etf.ip.backendapp.dto.GradeDTO;
import org.unibl.etf.ip.backendapp.entity.*;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.FacultyRepository;
import org.unibl.etf.ip.backendapp.repository.GradeRepository;
import org.unibl.etf.ip.backendapp.repository.InternshipRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class GradeService {

    private GradeRepository gradeRepository;
    private StudentRepository studentRepository;
    private ModelMapper modelMapper;
    private FacultyRepository facultyRepository;
    private InternshipRepository internshipRepository;

    public GradeService(GradeRepository gradeRepository, InternshipRepository internshipRepository, StudentRepository studentRepository, ModelMapper modelMapper, FacultyRepository facadeRepository) {
        this.gradeRepository = gradeRepository;
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.internshipRepository = internshipRepository;
        this.facultyRepository = facadeRepository;
    }

    public GradeDTO create(GradeDTO gradeDTO) {
        Faculty faculty = facultyRepository.findById(gradeDTO.getFacultyId()).orElseThrow(() -> new ResourceNotFoundException("Faculty does not exist."));
        Internship internship = internshipRepository.findById(gradeDTO.getInternshipId()).orElseThrow(() -> new ResourceNotFoundException("Internship does not exist."));
        Student student = studentRepository.findById(gradeDTO.getStudentId()).orElseThrow(() -> new ResourceNotFoundException("Student does not exist."));

        Grade grade = modelMapper.map(gradeDTO, Grade.class);
        grade.setFaculty(faculty);
        grade.setStudent(student);
        grade.setInternship(internship);
        Grade saved = gradeRepository.save(grade);
        return modelMapper.map(saved, GradeDTO.class);
    }


    public boolean delete(int gradeId) {
        if(gradeRepository.existsById(gradeId)) {
            gradeRepository.deleteById(gradeId);
            return true;
        }
        return false;
    }

    public List<GradeDTO> getForStudent(Integer studentId) {
        return gradeRepository.findByStudentId(studentId).stream().map(grade -> modelMapper.map(grade, GradeDTO.class)).collect(Collectors.toList());
    }

    public List<GradeDTO> getForFaculty(Integer facultyId) {
        return gradeRepository.findByFacultyId(facultyId).stream().map(grade -> modelMapper.map(grade, GradeDTO.class)).collect(Collectors.toList());
    }

    public List<GradeDTO> getForInternship(Integer internshipId) {
        return gradeRepository.findByInternshipId(internshipId).stream().map(grade -> modelMapper.map(grade, GradeDTO.class)).collect(Collectors.toList());
    }

    public List<GradeDTO> getForStudentAndFaculty(Integer studentId, Integer facultyId) {
        return gradeRepository.findByStudentIdAndFacultyId(studentId, facultyId).stream().map(grade -> modelMapper.map(grade, GradeDTO.class)).collect(Collectors.toList());
    }

    public GradeDTO getById(int gradeId) {
        Grade grade = gradeRepository.findById(gradeId).orElseThrow(() -> new ResourceNotFoundException("Grade with id " + gradeId + " not found"));
        return modelMapper.map(grade, GradeDTO.class);
    }
}
