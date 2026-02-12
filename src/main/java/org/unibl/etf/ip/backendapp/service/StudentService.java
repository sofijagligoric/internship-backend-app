package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.unibl.etf.ip.backendapp.dto.CreateUserDTO;
import org.unibl.etf.ip.backendapp.dto.InternshipDTO;
import org.unibl.etf.ip.backendapp.dto.StudentDTO;
import org.unibl.etf.ip.backendapp.entity.*;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.FacultyRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;
import org.unibl.etf.ip.backendapp.repository.UserRepository;
import org.unibl.etf.ip.backendapp.util.Role;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class StudentService {

    private final FacultyRepository facultyRepository;
    private StudentRepository studentRepository;
    private UserRepository userRepository;
    private ModelMapper modelMapper;
    private PasswordEncoder passwordEncoder;

    public StudentService(StudentRepository studentRepository, UserRepository userRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.facultyRepository = facultyRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll().stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    public List<StudentDTO> getStudentsByFacultyId(Integer facultyId) {
        return studentRepository.findByFacultyId(facultyId).stream().map(student -> modelMapper.map(student, StudentDTO.class)).collect(Collectors.toList());
    }

    public boolean deleteStudent(Integer studentId) {

        Student student = studentRepository.findById(studentId)
                .orElse(null);

        if (student == null) return false;

        userRepository.delete(student.getUser());
        studentRepository.delete(student);

        return true;
    }

    public StudentDTO createStudent(CreateUserDTO user, StudentDTO studentDTO) {
        User newUser = new User();
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoder.encode(user.getPassword()));
        newUser.setRole(Role.STUDENT);
        User savedUser = userRepository.save(newUser);
        Faculty faculty = facultyRepository.findById(studentDTO.getFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty does not exist."));
        Student student = modelMapper.map(studentDTO, Student.class);
        student.setFaculty(faculty);
        student.setUser(savedUser);
        Student studentSaved = studentRepository.save(student);
        return modelMapper.map(studentSaved, StudentDTO.class);
    }

    public StudentDTO updateStudent(Integer studentId, StudentDTO studentDTO) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));

        if(studentDTO.getFirstName() != null) {
            student.setFirstName(studentDTO.getFirstName());
        }
        if(studentDTO.getLastName() != null) {
            student.setLastName(studentDTO.getLastName());
        }
        if(studentDTO.getEmail() != null) {
            student.setEmail(studentDTO.getEmail());
        }
        if(studentDTO.getIndeks() != null) {
            student.setIndeks(studentDTO.getIndeks());
        }

        Student updatedStudent = studentRepository.save(student);
        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    public StudentDTO getStudentById(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));
        return modelMapper.map(student, StudentDTO.class);
    }
}
