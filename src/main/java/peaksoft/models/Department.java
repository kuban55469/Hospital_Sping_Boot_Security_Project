package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.*;
import static jakarta.persistence.FetchType.EAGER;
import static jakarta.persistence.GenerationType.SEQUENCE;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 17.02.2023
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "departments")
public class Department {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "department_id_gen")
    @SequenceGenerator(name = "department_id_gen", sequenceName = "department_id_seq", allocationSize = 1)
    private Long id;

    @NotEmpty(message = "The department name should not be empty.")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 3")
    private String name;


    @ManyToMany(mappedBy = "departments", cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = EAGER)
    private List<Doctor> doctors = new ArrayList<>();

    public void addDoctor(Doctor doctor) {
        if (doctors == null) {
            doctors = new ArrayList<>();
        }
        doctors.add(doctor);
    }

    @ManyToOne(cascade = {REFRESH, DETACH, MERGE, PERSIST}, fetch = EAGER)
    private Hospital hospital;


}