package com.example.vaccineManagement.Services;

import com.example.vaccineManagement.Exceptions.VaccinationAddressNotFound;
import com.example.vaccineManagement.Entity.VaccinationCenter;
import com.example.vaccineManagement.Repository.VaccinationCenterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VaccinationService {

    @Autowired
    VaccinationCenterRepository vaccinationCenterRepository;

    public String addVaccinationCenter(VaccinationCenter vaccinationCenter) throws VaccinationAddressNotFound {

        // 1. Address Validation (Pehle se maujood)
        if (vaccinationCenter.getAddress() == null) {
            throw new VaccinationAddressNotFound("Vaccination Address is Empty");
        }

        // 2. Dose Capacity Range Check (Min 2, Max 4)
        int capacity = vaccinationCenter.getDoseCapacity();

        if (capacity < 2 || capacity > 4) {
            throw new RuntimeException("Dose capacity must be between 2 and 4 only!");
        }

        // 3. Save to Database
        vaccinationCenterRepository.save(vaccinationCenter);

        return "Vaccination center added at a location " + vaccinationCenter.getAddress();
    }

    public List<VaccinationCenter> getAllVaccinationCenters() {
        return vaccinationCenterRepository.findAll();
    }
}
