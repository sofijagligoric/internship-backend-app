package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Faculty;
import org.unibl.etf.ip.backendapp.entity.Student;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    List<Student> findByFacultyId(Integer facultyId);
}
