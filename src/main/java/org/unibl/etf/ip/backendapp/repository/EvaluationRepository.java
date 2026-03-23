package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Evaluation;

import java.util.List;
import java.util.Optional;

public interface EvaluationRepository extends JpaRepository<Evaluation, Integer> {
    Optional<Evaluation> findByStudentIdAndInternshipId(int studentId, int internshipId);
    List<Evaluation> findByStudentId(int studentId);
    List<Evaluation> findByCompanyId(int companyId);
    boolean existsByStudentIdAndInternshipId(int studentId, int internshipId);
}
