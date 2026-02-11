package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.GradeDTO;
import org.unibl.etf.ip.backendapp.entity.Grade;
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
        if(!facultyRepository.existsById(gradeDTO.getFacultyId())) {
            throw new RuntimeException("Faculty does not exist");
        }
        if(!studentRepository.existsById(gradeDTO.getStudentId())) {
            throw new RuntimeException("Student does not exist");
        }
        if(!internshipRepository.existsById(gradeDTO.getInternshipId())) {
            throw new RuntimeException("Internship does not exist");
        }
        Grade grade = modelMapper.map(gradeDTO, Grade.class);
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
        return modelMapper.map(gradeRepository.findById(gradeId).orElse(null), GradeDTO.class);
    }
}
