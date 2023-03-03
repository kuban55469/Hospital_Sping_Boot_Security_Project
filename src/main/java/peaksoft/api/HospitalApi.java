package peaksoft.api;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Hospital;
import peaksoft.services.HospitalService;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/hospitals")
public class HospitalApi {

    private final HospitalService hospitalService;

    @GetMapping()
    public String getAllHospitals(Model model) {
        model.addAttribute("hospitals", hospitalService.findAll());
        return "hospital/hospitalPage";
    }

    @GetMapping("/new")
    public String create(Model model) {
        model.addAttribute("newHospital", new Hospital());
        return "hospital/newHospital";
    }


    @PostMapping("/save")
    public String save(@ModelAttribute("newHospital") @Valid Hospital hospital,
                       BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospital/newHospital";
        }

        hospitalService.save(hospital);
        return "redirect:/hospitals";
    }


    @GetMapping("/{hospitalId}/delete")
    public String deleteHospital(@PathVariable Long hospitalId) {
        hospitalService.deleteHospital(hospitalId);
        return "redirect:/hospitals";
    }

    @GetMapping("/{hospitalId}/edit")
    public String edit(@PathVariable Long hospitalId, Model model) {
        model.addAttribute("hospital", hospitalService.getHospitalById(hospitalId));
        return "hospital/update";
    }

    @PostMapping("/{id}/update")
    public String update(@ModelAttribute @Valid Hospital hospital,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "hospital/update";
        }
        hospitalService.update(hospital);
        return "redirect:/hospitals";
    }


}
