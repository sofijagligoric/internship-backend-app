package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.backendapp.entity.Application;
import org.unibl.etf.ip.backendapp.entity.Student;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByStudentId(int id);
    List<Application> findByInternshipId(int id);
    List<Application> findByStudentIdAndInternshipId(int studentId, int internshipId);
    boolean existsByStudentIdAndInternshipId(int studentId, int internshipId);
    List<Application> findAllByInternshipCompanyId(int companyId);
}
