package com.networkoutage.Technician_service.services;



import java.util.List;

import org.springframework.stereotype.Service;

import com.networkoutage.Technician_service.entities.Technician;
import com.networkoutage.Technician_service.repository.TechnicianRepository;



@Service
public class TechnicianService {

    private final TechnicianRepository technicianRepository;

    public TechnicianService(TechnicianRepository technicianRepository) {
        this.technicianRepository = technicianRepository;
    }

    public Technician createTechnician(Technician technician) {
        return technicianRepository.save(technician);
    }

    public List<Technician> getAllTechnicians() {
        return technicianRepository.findAll();
    }

    public Technician getTechnicianById(Long id) {
        return technicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technician not found"));
    }

    public Technician updateTechnician(Long id, Technician updatedTechnician) {

        Technician existingTechnician = technicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        existingTechnician.setName(updatedTechnician.getName());
        existingTechnician.setEmail(updatedTechnician.getEmail());
        existingTechnician.setPhone(updatedTechnician.getPhone());
        existingTechnician.setSpecialization(updatedTechnician.getSpecialization());
        existingTechnician.setAvailable(updatedTechnician.isAvailable());

        return technicianRepository.save(existingTechnician);
    }

    public void deleteTechnician(Long id) {

        Technician existingTechnician = technicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        technicianRepository.delete(existingTechnician);
    }
    
    public List<Technician> getAvailableTechnicians() {
        return technicianRepository.findByAvailableTrue();
    }
    
    public Technician markUnavailable(Long id) {

        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        technician.setAvailable(false);

        return technicianRepository.save(technician);
    }
    public Technician markAvailable(Long id) {

        Technician technician = technicianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Technician not found"));

        technician.setAvailable(true);

        return technicianRepository.save(technician);
    }
}