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
import peaksoft.repositories.HospitalRepo;
import peaksoft.services.DepartmentService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Service
@RequiredArgsConstructor
@Transactional
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepo departmentRepo;
    private final HospitalRepo hospitalRepo;
    private final AppointmentRepo appointmentRepo;


    @Override
    public List<Department> findAll(Long hospitalId) {
        Hospital hospital = hospitalRepo.findById(hospitalId).get();
        return new ArrayList<>(hospital.getDepartments());
    }


    @Override
    public void save(Long hospitalId, Department department) {
        Hospital hospital = hospitalRepo.findById(hospitalId).get();
        List<Department> departments = new ArrayList<>(hospital.getDepartments());
        for (Department dep : departments) {
            if (dep.getName().equals(department.getName())) {
                System.out.println("error");
            }
        }
        hospital.addDepartment(department);
        department.setHospital(hospital);
        departmentRepo.save(department);
    }


    @Override
    public Department findById(Long departmentId) {
        return departmentRepo.findById(departmentId).get();
    }

    @Override
    public void update(Long departmentId, Department department) {
        Department department1 = departmentRepo.findById(departmentId).get();
        department1.setName(department.getName());
        departmentRepo.save(department1);
    }


    @Override
    public void deleteDepartment(Long id, Long hospitalId) {
        Department department = departmentRepo.getById(id);
        List<Appointment> appointments = department.getHospital().getAppointments();
        Hospital hospital = hospitalRepo.findById(hospitalId).get();


        if (appointments != null) {
            List<Appointment> appointmentList = appointments.stream().filter(s -> s.getDepartment().getId().equals(id)).toList();
            appointmentList.forEach(s -> appointmentRepo.deleteById(s.getId()));
        }

        List<Department> departments = department.getHospital().getDepartments();
        departments.removeIf(s -> s.getId().equals(id));

        List<Doctor> doctors = department.getDoctors();
        doctors.forEach(d -> d.getDepartments().removeIf(s -> s.getId().equals(id)));

        if (appointments != null) {
            hospital.getAppointments().removeAll(appointments);
        }


        assert appointments != null;
        for (Appointment appointment : appointments) {
            appointmentRepo.deleteById(appointment.getId());
        }

        departmentRepo.deleteById(id);
    }

}
