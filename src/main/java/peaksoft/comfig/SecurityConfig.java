package peaksoft.comfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

/**
 * @author :ЛОКИ Kelsivbekov
 * @created 02.03.2023
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetails() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();

        UserDetails admin = userBuilder
                .username("Kuban")
                .password("12345")
                .roles("ADMIN")
                .build();
        UserDetails reception = userBuilder
                .username("Asel")
                .password("12345")
                .roles("RECEPTION")
                .build();
        UserDetails chiefDoctor = userBuilder
                .username("Isa")
                .password("12345")
                .roles("CHIEF_DOCTOR")
                .build();
        UserDetails patient = userBuilder
                .username("Janysh")
                .password("12345")
                .roles("PATIENT")
                .build();
        return new InMemoryUserDetailsManager(admin, reception, chiefDoctor, patient);
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()
                .requestMatchers("/hospitals").hasAnyRole("ADMIN", "RECEPTION", "CHIEF_DOCTOR", "PATIENT")
                .requestMatchers("/hospitals/new").hasAnyRole("ADMIN")
                .requestMatchers("/hospitals/save").hasAnyRole("ADMIN")
                .requestMatchers("/hospitals/{hospitalId}/edit").hasAnyRole("ADMIN")
                .requestMatchers("/hospitals/{id}/update").hasAnyRole("ADMIN")
                .requestMatchers("/hospitals/{hospitalId}/delete").hasAnyRole("ADMIN")


                .requestMatchers("/departments/{id}").hasAnyRole("ADMIN", "RECEPTION", "CHIEF_DOCTOR", "PATIENT")
                .requestMatchers("/departments/save/{hospitalId}").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/departments/new/{id}").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/departments/{hospitalId}/{departmentId}/assignDoctor").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/departments/edit/{departmentId}").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/departments/{hospitalId}/{departmentId}/update").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/departments/{hospitalId}/{departmentId}/delete").hasAnyRole("ADMIN", "CHIEF_DOCTOR")

                .requestMatchers("/doctors/{hospitalId}").hasAnyRole("ADMIN", "RECEPTION", "CHIEF_DOCTOR", "PATIENT")
                .requestMatchers("/doctors/save/{hospitalId}").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/doctors/new/{id}").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/doctors/edit/{doctorId}").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/doctors/{hospitalId}/{doctorId}/update").hasAnyRole("ADMIN", "CHIEF_DOCTOR")
                .requestMatchers("/doctors/{hospitalId}/{doctorId}/delete").hasAnyRole("ADMIN", "CHIEF_DOCTOR")


                .requestMatchers("/patients/{hospitalId}").hasAnyRole("ADMIN", "RECEPTION", "CHIEF_DOCTOR", "PATIENT")
                .requestMatchers("/patients/save/{hospitalId}").hasAnyRole("ADMIN", "RECEPTION")
                .requestMatchers("/patients/new/{id}").hasAnyRole("ADMIN", "RECEPTION")
                .requestMatchers("/patients/edit/{patientId}").hasAnyRole("ADMIN", "RECEPTION")
                .requestMatchers("/patients/{hospitalId}/{patientId}/update").hasAnyRole("ADMIN", "RECEPTION")
                .requestMatchers("/patients/{hospitalId}/{patientId}/delete").hasAnyRole("ADMIN", "RECEPTION")


                .requestMatchers("/appointments/{hospitalId}").hasAnyRole("ADMIN", "RECEPTION", "PATIENT")
                .requestMatchers("/appointments/save/{hospitalId}").hasAnyRole("ADMIN", "RECEPTION")
                .requestMatchers("/appointments/new/{hospitalId}").hasAnyRole("ADMIN", "RECEPTION")
                .requestMatchers("/appointments/edit/{appointmentId}").hasAnyRole("ADMIN", "RECEPTION")
                .requestMatchers("/appointments/{hospitalId}/{appointmentId}/update").hasAnyRole("ADMIN", "RECEPTION")
                .requestMatchers("/appointments/{hospitalId}/{appointmentId}/delete").hasAnyRole("ADMIN", "RECEPTION")


                .and()
                .formLogin()
                .defaultSuccessUrl("/hospitals")
                .permitAll();
        return http.build();
    }
}
