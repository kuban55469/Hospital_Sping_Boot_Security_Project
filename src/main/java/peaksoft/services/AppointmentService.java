package peaksoft.services;


import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;

import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Service

public interface AppointmentService {
    List<Appointment> findAll(Long id);

    Appointment findById(Long appointmentId);

    void update(Long appointmentId,Appointment appointment);

    Appointment save(Long hospitalId, Appointment appointment);

    void deleteAppointment(Long hospitalId, Long appointmentId);

}
