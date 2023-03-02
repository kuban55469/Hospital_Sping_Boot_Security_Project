package peaksoft.api;


import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import peaksoft.models.Patient;
import peaksoft.models.enums.Gender;
import peaksoft.services.PatientService;


/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Controller

@RequestMapping("/patients")
public class PatientApi {

    private final PatientService patientService;

    @Autowired
    public PatientApi(PatientService patientService) {
        this.patientService = patientService;
    }


    @GetMapping("/{id}")
    public String getAllPatients(Model model, @PathVariable Long id) {
        model.addAttribute("patients", patientService.findAll(id));
        model.addAttribute("hospitalId", id);
        return "patient/patientPage";
    }


    @GetMapping("/new/{id}")
    public String create(Model model,
                         @PathVariable("id") Long id) {
        model.addAttribute("newPatient", new Patient());
        model.addAttribute("hospitalId", id);
        model.addAttribute("male", Gender.MALE);
        model.addAttribute("female", Gender.FEMALE);
        return "/patient/savePatient";
    }

    @PostMapping("/save/{hospitalId}")
    public String save(@ModelAttribute("newPatient") @Valid Patient patient,
                       BindingResult bindingResult,
                       @PathVariable Long hospitalId) {

        if (bindingResult.hasErrors()) {
            return "/patient/savePatient";
        }
        patientService.save(hospitalId, patient);
        return "redirect:/patients/" + hospitalId;

    }


    @GetMapping("/edit/{patientId}")
    public String edit(@PathVariable("patientId") Long patientId,
                       Model model) {
        Patient patient = patientService.findById(patientId);
        model.addAttribute("patient", patient);
        model.addAttribute("hospitalId", patient.getHospital().getId());
        model.addAttribute("male", Gender.MALE);
        model.addAttribute("female", Gender.FEMALE);
        return "/patient/update";
    }

    @PostMapping("/{hospitalId}/{patientId}/update")
    public String update(@ModelAttribute("patient") @Valid Patient patient, BindingResult bindingResult,
                         @PathVariable("patientId") Long patientId,
                         @PathVariable("hospitalId") Long hospitalId) {

        if (bindingResult.hasErrors()) {
            return "/patient/update";
        }
        patientService.update(patientId, patient);
        return "redirect:/patients/" + hospitalId;
    }


    @GetMapping("/{hospitalId}/{patientId}/delete")
    public String deletePatient(@PathVariable("patientId") Long id,
                                @PathVariable("hospitalId") Long hospitalId) {
        patientService.deletePatient(id);
        return "redirect:/patients/" + hospitalId;
    }

}
