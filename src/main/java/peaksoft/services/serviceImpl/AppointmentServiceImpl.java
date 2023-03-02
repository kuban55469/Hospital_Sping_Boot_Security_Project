package peaksoft.services.serviceImpl;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.models.Appointment;
import peaksoft.models.Doctor;
import peaksoft.models.Hospital;
import peaksoft.models.Patient;
import peaksoft.repositories.*;
import peaksoft.services.AppointmentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Service
@RequiredArgsConstructor
@Transactional
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepo appointmentRepo;
    private final DepartmentRepo departmentRepo;
    private final PatientRepo patientRepo;
    private final DoctorRepo doctorRepo;
    private final HospitalRepo hospitalRepo;


    @Override
    public List<Appointment> findAll(Long id) {
        Hospital hospital = hospitalRepo.findById(id).get();
        return new ArrayList<>(hospital.getAppointments());
    }

    @Override
    public Appointment findById(Long appointmentId) {
        return appointmentRepo.findById(appointmentId).get();
    }

    @Override
    public void update(Long appointmentId, Appointment appointment) {
        Appointment appointment1 = appointmentRepo.findById(appointmentId).get();
        appointment1.setDate(appointment.getDate());
        appointmentRepo.save(appointment1);
    }

    @Override
    public Appointment save(Long hospitalId, Appointment appointment) {

        try {
            Hospital hospital = hospitalRepo.findById(hospitalId).get();
            Appointment newAppointment = new Appointment();
            newAppointment.setDate(appointment.getDate());
            newAppointment.setId(appointment.getId());

            newAppointment.setDoctor(doctorRepo.findById(appointment.getDoctorId()).get());
            newAppointment.setDepartment(departmentRepo.findById(appointment.getDepartmentId()).get());
            newAppointment.setPatient(patientRepo.findById(appointment.getPatientId()).get());
            hospital.addAppointment(newAppointment);
            return appointmentRepo.save(newAppointment);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteAppointment(Long hospitalId, Long appointmentId) {
//        Appointment appointment = appointmentRepo.findById(appointmentId).get();
//        Hospital hospital = hospitalRepo.findById(hospitalId).get();
//        appointment.getDoctor().setAppointments(null);
//        appointment.getPatient().setAppointments(null);
//
//        hospital.getAppointments().remove(appointment);
//
//        appointmentRepo.delete(appointment);

        Appointment existAppointment = appointmentRepo.getById(appointmentId);
        Hospital existHospital = existAppointment.getDoctor().getHospital();
        existHospital.getAppointments().remove(existAppointment);
        for (Doctor doctor : existHospital.getDoctors()) {
            doctor.getAppointments().remove(existAppointment);
        }
        for (Patient patient : existHospital.getPatients()) {
            patient.getAppointments().remove(existAppointment);
        }
        appointmentRepo.deleteById(appointmentId);

    }


}
