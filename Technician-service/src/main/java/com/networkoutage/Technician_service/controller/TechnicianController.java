package com.networkoutage.Technician_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.networkoutage.Technician_service.entities.Technician;
import com.networkoutage.Technician_service.services.TechnicianService;

@RestController
@RequestMapping("/technicians")
public class TechnicianController {

    private final TechnicianService technicianService;

    public TechnicianController(TechnicianService technicianService) {
        this.technicianService = technicianService;
    }

    @PostMapping
    public Technician createTechnician(@RequestBody Technician technician) {
        return technicianService.createTechnician(technician);
    }

    @GetMapping
    public List<Technician> getAllTechnicians() {
        return technicianService.getAllTechnicians();
    }

    @GetMapping("/{id}")
    public Technician getTechnicianById(@PathVariable Long id) {
        return technicianService.getTechnicianById(id);
    }

    @PutMapping("/{id}")
    public Technician updateTechnician(
            @PathVariable Long id,
            @RequestBody Technician technician) {

        return technicianService.updateTechnician(id, technician);
    }

    @DeleteMapping("/{id}")
    public String deleteTechnician(@PathVariable Long id) {
        technicianService.deleteTechnician(id);
        return "Technician deleted successfully";
    }
    @GetMapping("/available")
    public List<Technician> getAvailableTechnicians() {
        return technicianService.getAvailableTechnicians();
    }
    
    @PutMapping("/{id}/unavailable")
    public Technician markUnavailable(@PathVariable Long id) {
        return technicianService.markUnavailable(id);
    }
    
    @PutMapping("/{id}/available")
    public Technician markAvailable(@PathVariable Long id) {
        return technicianService.markAvailable(id);
    }

}