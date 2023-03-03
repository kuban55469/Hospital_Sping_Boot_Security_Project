package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
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
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "doctor_id_gen")
    @SequenceGenerator(name = "doctor_id_gen", sequenceName = "doctor_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "first_name")
    @NotEmpty(message = "The name should not be empty.")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 3")
    private String firsName;
    @Column(name = "last_name")
    @NotEmpty(message = "The name should not be empty.")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 3")
    private String lastName;

    @NotEmpty(message = "The position should not be empty.")
    @Size(min = 2, max = 30, message = "Name position be between 2 and 3")
    private String position;

    @NotEmpty(message = "Email should not be empty.")
    @Email(message = "Email must be valid")
    @Column(name = "email", unique = true)
    private String email;

    @ManyToMany(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = EAGER)
    private List<Department> departments = new ArrayList<>();

    public void addDepartment(Department department) {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        departments.add(department);
    }

    @OneToMany(mappedBy = "doctor", cascade = {ALL}, fetch = EAGER)
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToOne(cascade = {DETACH, MERGE, PERSIST, REFRESH}, fetch = EAGER)
    private Hospital hospital;


}
