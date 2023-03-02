package peaksoft.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.models.Patient;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Repository
public interface PatientRepo extends JpaRepository<Patient, Long> {
//    List<Patient> findAll(Long id);
//
//    Patient findById(Long patientId);
//
//    void update(Long id,Patient patient);
//
//    void save(Patient patient);
//
//    void deletePatient(Long id);
}
