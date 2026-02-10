package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Integer> {
}
