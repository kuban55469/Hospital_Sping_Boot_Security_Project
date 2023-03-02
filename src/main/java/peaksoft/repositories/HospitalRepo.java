package peaksoft.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.models.Hospital;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Repository
public interface HospitalRepo extends JpaRepository<Hospital, Long> {
//    List<Hospital> findAll();
//
//    void save(Hospital hospital);
//
//    void deleteHospital(Long hospitalId);
//
//    Hospital getHospitalById(Long hospitalId);
//
//
//    void update(Hospital updateHospital);
}
