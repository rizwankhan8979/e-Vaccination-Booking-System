package com.example.vaccineManagement.Services;

import com.example.vaccineManagement.Dtos.RequestDtos.AppointmentReqDto;
import com.example.vaccineManagement.Entity.*;
import com.example.vaccineManagement.Exceptions.DoctorNotFound;
import com.example.vaccineManagement.Exceptions.UserNotFound;
import com.example.vaccineManagement.Exceptions.VaccineNotFound;
import com.example.vaccineManagement.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppointmentService {

        @Autowired
        private AppointmentRepository appointmentRepository;

        @Autowired
        private DoctorRepository doctorRepository;

        @Autowired
        private VaccineRepository vaccineRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private AuthUserRepository authUserRepository;

        @Autowired
        private JavaMailSender emailSender;

        // BOOK APPOINTMENT (SECURE VERSION)
        public String bookAppointment(AppointmentReqDto dto)
                        throws DoctorNotFound, VaccineNotFound, UserNotFound {

                // Doctor
                Doctor doctor = doctorRepository.findById(dto.getDocId())
                                .orElseThrow(() -> new DoctorNotFound("Doctor not found"));

                // Vaccine
                Vaccine vaccine = vaccineRepository.findById(dto.getVaccineId())
                                .orElseThrow(() -> new VaccineNotFound("Vaccine not found"));

                // IMPORTANT CHECK (missing earlier)
                if (vaccine.getDoctor() == null ||
                                !vaccine.getDoctor().getDocId().equals(doctor.getDocId())) {
                        throw new RuntimeException("Selected vaccine does not belong to this doctor");
                }

                // User profile (Vaccinee)
                User user;
                if (dto.getUserId() != null) {
                        user = userRepository.findById(dto.getUserId())
                                        .orElseThrow(() -> new UserNotFound(
                                                        "User not found with ID: " + dto.getUserId()));
                } else {
                        // Logged-in email (from SecurityContext)
                        String email = SecurityContextHolder.getContext()
                                        .getAuthentication()
                                        .getName();

                        // AuthUser
                        AuthUser authUser = authUserRepository.findByEmail(email)
                                        .orElseThrow(() -> new RuntimeException("Auth user not found"));

                        // User profile
                        user = userRepository.findByAuthUser(authUser)
                                        .orElseThrow(() -> new UserNotFound("User profile not found"));
                }

                // Appointment
                Appointment appointment = new Appointment();
                appointment.setAppointmentDate(dto.getAppointmentDate());
                appointment.setAppointmentTime(dto.getAppointmentTime());
                appointment.setDoctor(doctor);
                appointment.setVaccine(vaccine);
                appointment.setUser(user);

                appointmentRepository.save(appointment);

                // Confirmation Email
                if (user.getAuthUser() != null) {
                        try {
                                SimpleMailMessage mail = new SimpleMailMessage();
                                mail.setTo(user.getAuthUser().getEmail());
                                mail.setSubject("Appointment Confirmed");
                                mail.setText(
                                                "Hi " + user.getName() + ",\n\n" +
                                                                "Your appointment is confirmed.\n\n" +
                                                                "Doctor: " + doctor.getName() + "\n" +
                                                                "Vaccine: " + vaccine.getVaccineName() + "\n\n" +
                                                                "Stay safe!");
                                emailSender.send(mail);
                        } catch (Exception e) {
                                System.out.println("Failed to send email: " + e.getMessage());
                        }
                }

                return "Appointment booked successfully";
        }
}
