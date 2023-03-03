package peaksoft.services.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.models.*;
import peaksoft.repositories.AppointmentRepo;
import peaksoft.repositories.HospitalRepo;
import peaksoft.repositories.PatientRepo;
import peaksoft.services.PatientService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final PatientRepo patientRepo;
    private final HospitalRepo hospitalRepo;
    private final AppointmentRepo appointmentRepo;


    @Override
    public List<Patient> findAll(Long id) {
        Hospital hospital = hospitalRepo.findById(id).get();
        return new ArrayList<>(hospital.getPatients());
    }

    @Override
    public Patient findById(Long patientId) {
        return patientRepo.findById(patientId).get();
    }

    @Override
    public void update(Long patientId, Patient patient) {
        Patient patient1 = patientRepo.findById(patientId).get();
        patient1.setFirstName(patient.getFirstName());
        patient1.setLastName(patient.getLastName());
        patient1.setGender(patient.getGender());
        patient1.setEmail(patient.getEmail());
        patientRepo.save(patient1);
    }

    @Override
    public void save(Long hospitalId, Patient patient) {
        Hospital hospitalById = hospitalRepo.findById(hospitalId).get();
        hospitalById.addPatient(patient);
        patient.setHospital(hospitalById);
        patientRepo.save(patient);
    }


    @Override
    public void deletePatient(Long id) {
        Patient patient = patientRepo.findById(id).get();
        Hospital hospital = patient.getHospital();
        List<Appointment> appointments = patient.getAppointments();
        appointments.forEach(a -> a.getPatient().setAppointments(null));
        appointments.forEach(a -> a.getDoctor().setAppointments(null));
        hospital.getAppointments().removeAll(appointments);

        List<Patient> patients = patient.getHospital().getPatients();
        patients.removeIf(p -> p.getId().equals(id));

        for (Appointment appointment : appointments) {
            appointmentRepo.deleteById(appointment.getId());
        }
        patientRepo.deleteById(id);

    }
}


