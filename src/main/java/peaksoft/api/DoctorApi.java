package peaksoft.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Department;
import peaksoft.models.Doctor;
import peaksoft.services.DepartmentService;
import peaksoft.services.DoctorService;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorApi {

    private final DoctorService doctorService;
    private final DepartmentService departmentService;


    @GetMapping("/{hospitalId}")
    public String getAllDoctors(Model model,
                                @PathVariable Long hospitalId,
                                @ModelAttribute("department") Department department) {
        model.addAttribute("departments", departmentService.findAll(hospitalId));
        model.addAttribute("doctors", doctorService.getAllDoctors(hospitalId));
        model.addAttribute("hospitalId", hospitalId);
        return "/doctor/doctorPage";
    }

    @PostMapping("/save/{hospitalId}")
    public String save(@ModelAttribute("newDoctor") @Valid Doctor doctor,
                       BindingResult bindingResult,
                       @PathVariable Long hospitalId) {
        if (bindingResult.hasErrors()) {
            return "/doctor/saveDoctor";
        }
        doctorService.save(hospitalId, doctor);
        return "redirect:/doctors/" + hospitalId;
    }

    @GetMapping("/new/{id}")
    public String create(Model model,
                         @PathVariable Long id) {
        model.addAttribute("newDoctor", new Doctor());
        model.addAttribute("hospitalId", id);
        return "/doctor/saveDoctor";
    }

    @GetMapping("/edit/{doctorId}")
    public String edit(@PathVariable Long doctorId,
                       Model model) {
        Doctor doctor = doctorService.findById(doctorId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("hospitalId", doctor.getHospital().getId());
        return "/doctor/update";
    }

    @PostMapping("/{hospitalId}/{doctorId}/update")
    public String update(@ModelAttribute("doctor") @Valid Doctor doctor,
                         BindingResult bindingResult,
                         @PathVariable Long doctorId,
                         @PathVariable Long hospitalId) {
        if (bindingResult.hasErrors()) {
            return "/doctor/update";
        }

        doctorService.update(doctorId, doctor);
        return "redirect:/doctors/" + hospitalId;
    }


    @GetMapping("/{hospitalId}/{doctorId}/delete")
    public String deleteDoctor(@PathVariable Long doctorId,
                               @PathVariable Long hospitalId) {
        doctorService.deleteDoctor(doctorId);
        return "redirect:/doctors/" + hospitalId;
    }


}
