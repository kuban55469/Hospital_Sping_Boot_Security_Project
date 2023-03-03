package peaksoft.services.serviceImpl;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import peaksoft.models.Appointment;
import peaksoft.models.Department;
import peaksoft.models.Doctor;
import peaksoft.models.Hospital;
import peaksoft.repositories.AppointmentRepo;
import peaksoft.repositories.DepartmentRepo;
import peaksoft.repositories.DoctorRepo;
import peaksoft.repositories.HospitalRepo;
import peaksoft.services.DoctorService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Service
@Transactional
@RequiredArgsConstructor
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepo doctorRepo;
    private final HospitalRepo hospitalRepo;

    private final AppointmentRepo appointmentRepo;
    private final DepartmentRepo departmentRepo;


    @Override
    public List<Doctor> getAllDoctors(Long id) {
        Hospital hospital = hospitalRepo.findById(id).get();
        return new ArrayList<>(hospital.getDoctors());
    }

    @Override
    public void save(Long hospitalId, Doctor doctor) {
        Hospital hospital = hospitalRepo.findById(hospitalId).get();
        hospital.addDoctor(doctor);
        doctor.setHospital(hospital);
        doctorRepo.save(doctor);
    }

    @Override
    public Doctor findById(Long doctorId) {
        return doctorRepo.findById(doctorId).get();
    }

    @Override
    public void update(Long doctorId, Doctor doctor) {
        Doctor doctor1 = doctorRepo.findById(doctorId).get();
        doctor1.setFirsName(doctor.getFirsName());
        doctor1.setLastName(doctor.getLastName());
        doctor1.setEmail(doctor.getEmail());
        doctor1.setPosition(doctor.getPosition());
        doctorRepo.save(doctor1);
    }


    @Override
    public void assignDoctor(Long departmentId, Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).get();
        Department department = departmentRepo.findById(departmentId).get();
        department.addDoctor(doctor);
        doctor.addDepartment(department);

    }

    @Override
    public void deleteDoctor(Long id) {
        Doctor doctor = doctorRepo.findById(id).get();
        Hospital hospital = doctor.getHospital();

        List<Appointment> appointments = doctor.getAppointments();

        appointments.forEach(a -> a.getDoctor().setAppointments(null));

        appointments.forEach(a -> a.getPatient().setAppointments(null));

        hospital.getAppointments().removeAll(appointments);

        List<Doctor> doctors = doctor.getHospital().getDoctors();
        doctors.removeIf(s -> s.getId().equals(id));

        List<Department> departments = doctor.getDepartments();
        departments.forEach(d -> d.getDoctors().removeIf(s -> s.getId().equals(id)));

        for (Appointment appointment : appointments) {
            appointmentRepo.deleteById(appointment.getId());
        }
        doctorRepo.deleteById(id);
    }
}
