package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Workentry;

import java.time.LocalDate;
import java.util.List;

public interface WorkentryRepository extends JpaRepository<Workentry, Integer> {
    List<Workentry> findByStudentId(int studentId);
    List<Workentry> findByInternshipId(int internshipId);
    List<Workentry> findByStudentIdAndInternshipId(int studentId, int internshipId);

    boolean existsByStudentIdAndInternshipIdAndWeekStart(Integer studentId, Integer internshipId, LocalDate weekStart);

}
