package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Cv;


public interface CVRepository extends JpaRepository<Cv, Integer> {
    Cv findByStudentId(Integer id);
    boolean existsByStudentId(Integer id);
}
