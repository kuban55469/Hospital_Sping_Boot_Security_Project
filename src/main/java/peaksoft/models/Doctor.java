package peaksoft.models;

import jakarta.persistence.*;
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
    private String firsName;
    @Column(name = "last_name")
    private String lastName;

    private String position;

    private String email;

    @ManyToMany( cascade = {DETACH,MERGE,PERSIST, REFRESH},fetch = EAGER)
    private List<Department> departments = new ArrayList<>();
    public void addDepartment(Department department) {
        if (departments == null) {
            departments= new ArrayList<>();
        }
        departments.add(department);
    }

    @OneToMany(mappedBy = "doctor", cascade = {ALL},fetch = EAGER)
    private List<Appointment> appointments = new ArrayList<>();

    @ManyToOne(cascade = {DETACH,MERGE,PERSIST, REFRESH},fetch = EAGER)
    private Hospital hospital;


}
