package com.example.vaccineManagement.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "vaccines")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String vaccineName;
    private String manufacturer;
    private int dosesRequired;

    private String ageRange;

    // Default to "Active"
    private String status = "Active";

    // Many vaccines belong to one doctor
    @ManyToOne
    @JoinColumn(name = "doctor_id") // foreign key
    @JsonIgnore
    private Doctor doctor;
}
