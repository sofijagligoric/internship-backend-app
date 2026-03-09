package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.unibl.etf.ip.backendapp.dto.ApplicationViewDTO;
import org.unibl.etf.ip.backendapp.entity.Application;
import org.unibl.etf.ip.backendapp.entity.Student;

import java.util.List;

public interface ApplicationRepository extends JpaRepository<Application, Integer> {
    List<Application> findByStudentId(int id);
    List<Application> findByInternshipId(int id);
    boolean existsByStudentIdAndInternshipId(int studentId, int internshipId);
    boolean existsByStudentIdAndInternshipIdAndStatus(int studentId, int internshipId, String status);
    List<Application> findAllByInternshipCompanyId(int companyId);
    List<Application> findAllByStatusAndInternshipCompanyId( String status, int companyId);
    List<Application> findAllByStudentIdAndStatus(int studentId, String status);
    @Query("SELECT new org.unibl.etf.ip.backendapp.dto.ApplicationViewDTO(" +
            "a.id, a.status, a.date, i.id, s.id, " +
            "concat(s.firstName, ' ', s.lastName), i.name) " +
            "FROM Application a " +
            "JOIN a.student s " +
            "JOIN a.internship i " +
            "WHERE s.id = :studentId")
    List<ApplicationViewDTO> findAllForStudent(@Param("studentId") Integer studentId);
    @Query("SELECT new org.unibl.etf.ip.backendapp.dto.ApplicationViewDTO(" +
            "a.id, a.status, a.date, i.id, s.id, " +
            "concat(s.firstName, ' ', s.lastName), i.name) " +
            "FROM Application a " +
            "JOIN a.student s " +
            "JOIN a.internship i " +
            "WHERE a.status = :status AND s.id = :studentId")
    List<ApplicationViewDTO> findAllForStudentWithStatus(@Param("status") String status, @Param("studentId") Integer studentId);
    @Query("SELECT new org.unibl.etf.ip.backendapp.dto.ApplicationViewDTO(" +
            "a.id, a.status, a.date, i.id, s.id, " +
            "concat(s.firstName, ' ', s.lastName), i.name) " +
            "FROM Application a " +
            "JOIN a.student s " +
            "JOIN a.internship i " +
            "WHERE a.status = :status AND i.company.id = :companyId")
    List<ApplicationViewDTO> findAllPendingDTO(@Param("status") String status, @Param("companyId") Integer companyId);

    @Query("SELECT new org.unibl.etf.ip.backendapp.dto.ApplicationViewDTO(" +
            "a.id, a.status, a.date, i.id, s.id, " +
            "concat(s.firstName, ' ', s.lastName), i.name) " +
            "FROM Application a " +
            "JOIN a.student s " +
            "JOIN a.internship i " +
            "WHERE i.company.id = :companyId")
    List<ApplicationViewDTO> findAllForComapny(@Param("companyId") Integer companyId);
}
