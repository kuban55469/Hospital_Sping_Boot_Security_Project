package peaksoft.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.models.Doctor;


import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Repository
public interface DoctorRepo extends JpaRepository<Doctor, Long> {
//    List<Doctor> getAllDoctors(Long id);
//
//    void save(Doctor doctor);
//
//    Doctor findById(Long doctorId);
//
//    void update(Long doctorId,Doctor doctor);
//
//    //1
//    void assignDoctor(Long departmentId, Long doctorId);
//
//    void deleteDoctor(Long id);
}
