package peaksoft.api;


import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Department;
import peaksoft.models.Doctor;
import peaksoft.services.DepartmentService;
import peaksoft.services.DoctorService;
import peaksoft.services.HospitalService;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/doctors")
public class DoctorApi{

    private final DoctorService doctorService;

    private final HospitalService hospitalService;

    private final DepartmentService departmentService;


    @GetMapping("/{hospitalId}")
    public String getAllDoctors(Model model,
                                @PathVariable("hospitalId") Long hospitalId,
                                @ModelAttribute("department") Department department){
        model.addAttribute("departments",departmentService.findAll(hospitalId));
        model.addAttribute("doctors",doctorService.getAllDoctors(hospitalId));
        model.addAttribute("hospitalId",hospitalId);
        return "/doctor/doctorPage";
    }

    @PostMapping("/save/{hospitalId}")
    public String save(@ModelAttribute("newDoctor") Doctor doctor,
                       @PathVariable("hospitalId") Long hospitalId){
        doctorService.save(hospitalId, doctor);
        return "redirect:/doctors/" + hospitalId;
    }

    @GetMapping("/new/{id}")
    public String create(Model model,
                         @PathVariable("id") Long id){
        model.addAttribute("newDoctor", new Doctor());
        model.addAttribute("hospitalId", id);
        return "/doctor/saveDoctor";
    }

    @GetMapping("/edit/{doctorId}")
    public String edit(@PathVariable("doctorId")Long doctorId,
                       Model model){
        Doctor doctor = doctorService.findById(doctorId);
        model.addAttribute("doctor", doctor);
        model.addAttribute("hospitalId",doctor.getHospital().getId());
        return "/doctor/update";
    }

    @PostMapping("/{hospitalId}/{doctorId}/update")
    public String update(@ModelAttribute("doctor") Doctor doctor,
                         @PathVariable("doctorId") Long doctorId,
                         @PathVariable("hospitalId") Long hospitalId){
        doctorService.update(doctorId,doctor);
        return "redirect:/doctors/" + hospitalId;
    }




    @GetMapping("/{hospitalId}/{doctorId}/delete")
    public String deleteDoctor(@PathVariable("doctorId")Long id,
                                @PathVariable("hospitalId")Long hospitalId){
        doctorService.deleteDoctor(id);
        return"redirect:/doctors/" + hospitalId;
    }














}
