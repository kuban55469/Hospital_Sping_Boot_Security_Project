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
@RequestMapping("/departments")
@RequiredArgsConstructor
public class DepartmentApi {

    private final DepartmentService departmentService;
    private final DoctorService doctorService;


    @GetMapping("/{id}")
    public String getAllDepartments(Model model,
                                    @PathVariable Long id,
                                    @ModelAttribute("doctor") Doctor doctor) {
        model.addAttribute("departments", departmentService.findAll(id));
        model.addAttribute("doctors", doctorService.getAllDoctors(id));
        model.addAttribute("hospitalId", id);
        return "department/departments";
    }


    @PostMapping("/save/{hospitalId}")
    public String save(@ModelAttribute("newDepartment") @Valid Department department,
                       BindingResult bindingResult,
                       @PathVariable Long hospitalId) {
        if (bindingResult.hasErrors()) {
            return "/department/saveDepartment";
        }
        departmentService.save(hospitalId, department);
        return "redirect:/departments/" + hospitalId;
    }

    @GetMapping("/new/{id}")
    public String create(Model model,
                         @PathVariable Long id) {
        model.addAttribute("newDepartment", new Department());
        model.addAttribute("hospitalId", id);
        return "/department/saveDepartment";
    }


    @PostMapping("{hospitalId}/{departmentId}/assignDoctor")
    private String assignDepartment(@PathVariable Long hospitalId,
                                    @PathVariable Long departmentId,
                                    @ModelAttribute("doctor") Doctor doctor) {
        Long id = doctor.getId();
        doctorService.assignDoctor(departmentId, id);
        return "redirect:/departments/" + hospitalId;
    }


    @GetMapping("/edit/{departmentId}")
    public String edit(@PathVariable Long departmentId,
                       Model model) {
        Department department = departmentService.findById(departmentId);
        model.addAttribute("department", department);
        model.addAttribute("hospitalId", department.getHospital().getId());
        return "/department/update";
    }

    @PostMapping("/{hospitalId}/{departmentId}/update")
    public String update(@ModelAttribute("department") @Valid Department department,
                         BindingResult bindingResult,
                         @PathVariable Long departmentId,
                         @PathVariable Long hospitalId) {
        if (bindingResult.hasErrors()) {
            return "/department/update";
        }

        departmentService.update(departmentId, department);
        return "redirect:/departments/" + hospitalId;
    }


    @GetMapping("/{hospitalId}/{departmentId}/delete")
    public String deletePatient(@PathVariable Long departmentId,
                                @PathVariable Long hospitalId) {
        departmentService.deleteDepartment(departmentId, hospitalId);
        return "redirect:/departments/" + hospitalId;
    }


}
