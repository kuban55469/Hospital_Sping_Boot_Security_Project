package peaksoft.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.models.Appointment;
import peaksoft.models.Hospital;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Long> {
//    List<Appointment> findAll(Long id);
//
//    Appointment findById(Long appointmentId);
//
//    void update(Long appointmentId, Appointment appointment);
//
//    Appointment save(Appointment appointment);
//
//    void deleteAppointment(Hospital hospital, Long appointmentId);
}
