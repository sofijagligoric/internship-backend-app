package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Cv;

import java.util.Optional;


public interface CVRepository extends JpaRepository<Cv, Integer> {
    Optional<Cv> findByStudentId(Integer studentId);
    boolean existsByStudentId(Integer id);
}
