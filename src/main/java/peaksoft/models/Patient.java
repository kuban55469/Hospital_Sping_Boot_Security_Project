package peaksoft.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import peaksoft.models.enums.Gender;

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
@Table(name = "patients")
public class Patient {
    @Id
    @GeneratedValue(strategy = SEQUENCE, generator = "patient_id_gen")
    @SequenceGenerator(name = "patient_id_gen", sequenceName = "patient_id_seq", allocationSize = 1)
    private Long id;
    @Column(name = "first_name")
    @NotEmpty(message = "The name should not be empty.")
    @Size(min = 2, max = 30, message = "Name must be between 2 and 30.")
    private String firstName;
    @Column(name = "last_name")
    @NotEmpty(message = "Last name should not be empty.")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30.")
    private String lastName;
    @Column(name = "phone_number")
    private String phoneNumber;
    private Gender gender;
    @NotEmpty(message = "Email should not be empty.")
    @Email(message = "Email must be valid")
    @Column(name = "email", unique = true)
    private String email;


    @ManyToOne(cascade = {DETACH,MERGE,PERSIST, REFRESH},fetch = EAGER)
    private Hospital hospital;

    @OneToMany(mappedBy = "patient", cascade = {ALL},fetch = EAGER)
    private List<Appointment> appointments = new ArrayList<>();
}
