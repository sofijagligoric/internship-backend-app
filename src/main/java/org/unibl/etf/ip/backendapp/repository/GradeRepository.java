package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Grade;

import java.util.List;

public interface GradeRepository extends JpaRepository<Grade, Integer> {
    List<Grade> findByStudentId(int studentId);
    List<Grade> findByStudentIdAndFacultyId(int studentId, int facultyId);
    List<Grade> findByFacultyId(int facultyId);
    List<Grade> findByFacultyIdAndStudentIdAndInternshipId(int facultyId, int studentId, int internshipId);
    boolean existsByStudentAndInternshipId(int studentId, int internshipId);
}
