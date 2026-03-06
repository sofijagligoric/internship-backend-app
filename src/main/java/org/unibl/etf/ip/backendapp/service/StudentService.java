package org.unibl.etf.ip.backendapp.service;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.unibl.etf.ip.backendapp.dto.CreateStudentDTO;
import org.unibl.etf.ip.backendapp.dto.StudentDTO;
import org.unibl.etf.ip.backendapp.entity.*;
import org.unibl.etf.ip.backendapp.exceptions.ResourceNotFoundException;
import org.unibl.etf.ip.backendapp.repository.FacultyRepository;
import org.unibl.etf.ip.backendapp.repository.StudentRepository;
import org.unibl.etf.ip.backendapp.repository.UserRepository;
import org.unibl.etf.ip.backendapp.util.Role;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
        studentRepository.delete(student);
        userRepository.delete(student.getUser());
        return true;
    }

    public StudentDTO createStudent(CreateStudentDTO newStudentDTO) {
        User newUser = new User();
        newUser.setUsername(newStudentDTO.getUsername());
        newUser.setPassword(passwordEncoder.encode(newStudentDTO.getPassword()));
        newUser.setRole(Role.STUDENT);
        User savedUser = userRepository.save(newUser);
        Faculty faculty = facultyRepository.findById(newStudentDTO.getFacultyId())
                .orElseThrow(() -> new ResourceNotFoundException("Faculty does not exist."));
        Student student = new Student();
        student.setIndeks(newStudentDTO.getIndeks());
        student.setFirstName(newStudentDTO.getFirstName());
        student.setLastName(newStudentDTO.getLastName());
        student.setEmail(newStudentDTO.getEmail());
        student.setFaculty(faculty);
        student.setUser(savedUser);
        Student studentSaved = studentRepository.save(student);
        return modelMapper.map(studentSaved, StudentDTO.class);
    }

    public StudentDTO updateStudent(Integer studentId, CreateStudentDTO studentDTO) {
        User u = userRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new ResourceNotFoundException("Student not found"));
        if(studentDTO.getUsername() != null)
        {
            u.setUsername(studentDTO.getUsername());
        }
        if(studentDTO.getPassword() != null && !studentDTO.getPassword().trim().isEmpty()) {
            u.setPassword(passwordEncoder.encode(studentDTO.getPassword()));
        }

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
        userRepository.save(u);
        Student updatedStudent = studentRepository.save(student);
        return modelMapper.map(updatedStudent, StudentDTO.class);
    }

    public StudentDTO getStudentById(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));
        return modelMapper.map(student, StudentDTO.class);
    }

    public CreateStudentDTO getCompleteStudentById(Integer studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student with id " + studentId + " not found"));
        User u = userRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        CreateStudentDTO result = new CreateStudentDTO();
        result.setFirstName(student.getFirstName());
        result.setLastName(student.getLastName());
        result.setEmail(student.getEmail());
        result.setIndeks(student.getIndeks());
        result.setUsername(u.getUsername());
        result.setPassword("");
        return result;
    }

    /*
    public boolean importFromCsv(MultipartFile file, Integer facultyId) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) {
                    throw new RuntimeException("Invalid CSV format");
                }
                CreateStudentDTO s = new CreateStudentDTO();
                s.setFirstName(data[0]);
                s.setLastName(data[1]);
                s.setIndeks(data[2]);
                s.setEmail(data[3]);
                s.setUsername(data[4]);
                s.setPassword(data[5]);
                s.setFacultyId(facultyId);
                this.createStudent(s);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

     */

    public boolean importFromCsv(InputStream inputStream, Integer facultyId) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length < 6) {
                    System.err.println("Invalid row: Not enough columns in CSV");
                    continue;
                }

                CreateStudentDTO s = new CreateStudentDTO();
                s.setFirstName(data[0]);
                s.setLastName(data[1]);
                s.setIndeks(data[2]);
                s.setEmail(data[3]);
                s.setUsername(data[4]);
                s.setPassword(data[5]);
                s.setFacultyId(facultyId);

                this.createStudent(s);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
