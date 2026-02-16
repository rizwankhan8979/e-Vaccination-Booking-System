package com.example.vaccineManagement.Repository;

import com.example.vaccineManagement.Entity.Vaccine;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface VaccineRepository extends JpaRepository<Vaccine, Integer> {
    // Find vaccines by doctor ID
    List<Vaccine> findByDoctor_DocId(int docId);
}
