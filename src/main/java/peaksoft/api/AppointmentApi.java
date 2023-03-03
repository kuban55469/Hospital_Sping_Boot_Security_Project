package peaksoft.api;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Appointment;
import peaksoft.services.AppointmentService;
import peaksoft.services.DepartmentService;
import peaksoft.services.DoctorService;
import peaksoft.services.PatientService;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Controller
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentApi {
    private final AppointmentService appointmentService;
    private final PatientService patientService;

    private final DoctorService doctorService;
    private final DepartmentService departmentService;

    @GetMapping("/{hospitalId}")
    public String getAllAppointments(Model model,
                                     @PathVariable Long hospitalId) {
        model.addAttribute("appointments", appointmentService.findAll(hospitalId));
        return "appointment/appointmentPage";
    }

    @GetMapping("/new/{hospitalId}")
    public String addAppointment(@PathVariable Long hospitalId,
                                 Model model) {
        model.addAttribute("newAppointment", new Appointment());
        model.addAttribute("patients", patientService.findAll(hospitalId));
        model.addAttribute("departments", departmentService.findAll(hospitalId));
        model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
        model.addAttribute(hospitalId);
        return "appointment/saveAppointment";
    }


    @PostMapping("/save/{hospitalId}")
    public String save(@PathVariable Long hospitalId,
                       @ModelAttribute("newAppointment") @Valid Appointment appointment,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "appointment/saveAppointment";
        }
        appointmentService.save(hospitalId, appointment);
        return "redirect:/appointments/" + hospitalId;
    }


    @GetMapping("/edit/{appointmentId}")
    public String edit(@PathVariable Long appointmentId, Model model) {
        Appointment appointment = appointmentService.findById(appointmentId);
        model.addAttribute("appointment", appointmentService.findById(appointmentId));
        model.addAttribute("hospitalId", appointment.getDoctor().getHospital().getId());
        return "appointment/update";
    }

    @PutMapping("/{hospitalId}/{appointmentId}/update")
    public String update(@ModelAttribute("appointment") @Valid Appointment appointment,
                         BindingResult bindingResult,
                         @PathVariable Long appointmentId,
                         @PathVariable Long hospitalId) {
        if (bindingResult.hasErrors()) {
            return "appointment/update";
        }

        appointmentService.update(appointmentId, appointment);
        return "redirect:/appointments/" + hospitalId;
    }


    @GetMapping("/{hospitalId}/{appointmentId}/delete")
    public String deleteDoctor(@PathVariable Long appointmentId,
                               @PathVariable Long hospitalId) {
        appointmentService.deleteAppointment(hospitalId, appointmentId);
        return "redirect:/appointments/" + hospitalId;
    }


}
