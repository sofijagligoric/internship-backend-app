package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Evaluation;

import java.util.List;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    List<Evaluation> findByStudentIdAndInternshipId(int studentId, int internshipId);
    List<Evaluation> findByStudentId(int studentId);
    List<Evaluation> findByInternshipId(int internshipId);
    List<Evaluation> findByCompanyId(int companyId);
    List<Evaluation> findByStudentIdAndCompanyId(int studentId, int companyId);
    boolean existsByStudentIdAndInternshipId(int studentId, int internshipId);
}
