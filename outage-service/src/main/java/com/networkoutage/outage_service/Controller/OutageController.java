package com.networkoutage.outage_service.Controller;



import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.networkoutage.outage_service.Service.OutageService;
import com.networkoutage.outage_service.dto.OutageResponse;
import com.networkoutage.outage_service.entity.Outage;

@RestController
@RequestMapping("/outages")
public class OutageController {

    private final OutageService outageService;

    public OutageController(OutageService outageService) {
        this.outageService = outageService;
    }

    @PostMapping
    public OutageResponse createOutage(@RequestBody Outage outage) {
        return outageService.createOutage(outage);
    }

    @GetMapping
    public List<Outage> getAllOutages() {
        return outageService.getAllOutages();
    }

    @GetMapping("/my")
    public List<Outage> getMyOutages(
            @RequestHeader("X-Technician-Id") Long technicianId) {

        return outageService.getOutagesByTechnician(technicianId);
    }

    @GetMapping("/technician/{technicianId}")
    public List<Outage> getOutagesByTechnician(
            @PathVariable Long technicianId) {

        return outageService.getOutagesByTechnician(technicianId);
    }

    @GetMapping("/available-technicians")
    public String getAvailableTechnicians() {
        return outageService.getAvailableTechnicians();
    }

    @GetMapping("/{id}")
    public Outage getOutageById(@PathVariable Long id) {
        return outageService.getOutageById(id);
    }

    @PutMapping("/{id}")
    public Outage updateOutage(
            @PathVariable Long id,
            @RequestBody Outage outage) {

        return outageService.updateOutage(id, outage);
    }

    @DeleteMapping("/{id}")
    public String deleteOutage(@PathVariable Long id) {

        outageService.deleteOutage(id);

        return "Outage deleted successfully";
    }

    @PutMapping("/{outageId}/assign/{technicianId}")
    public Outage assignTechnician(
            @PathVariable Long outageId,
            @PathVariable Long technicianId) {

        return outageService.assignTechnician(
                outageId,
                technicianId
        );
    }

    @PutMapping("/{outageId}/complete")
    public Outage completeOutage(
            @PathVariable Long outageId) {

        return outageService.completeOutage(outageId);
    }

    @PutMapping("/{outageId}/investigate")
    public Outage investigateOutage(
            @PathVariable Long outageId) {

        return outageService.investigateOutage(outageId);
    }

    @PutMapping("/{outageId}/progress")
    public Outage startProgress(
            @PathVariable Long outageId) {

        return outageService.startProgress(outageId);
    }
}

