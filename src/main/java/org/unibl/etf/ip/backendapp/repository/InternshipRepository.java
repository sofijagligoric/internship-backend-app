package org.unibl.etf.ip.backendapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.unibl.etf.ip.backendapp.entity.Internship;

import java.util.List;

public interface InternshipRepository extends JpaRepository<Internship, Integer> {
    List<Internship> findByCompanyId(int companyId);

    List<Internship> findByAvailable(Boolean available);
}
