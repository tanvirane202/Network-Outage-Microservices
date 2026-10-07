package com.networkoutage.outage_service.Service;




import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.networkoutage.outage_service.Repository.OutageRepository;
import com.networkoutage.outage_service.client.AIRecommendationClient;
import com.networkoutage.outage_service.client.TechnicianClient;
import com.networkoutage.outage_service.client.TechnicianResponse;
import com.networkoutage.outage_service.dto.OutageResponse;
import com.networkoutage.outage_service.entity.Outage;
import com.networkoutage.outage_service.entity.OutageStatus;
import com.networkoutage.outage_service.kafka.OutageEventProducer;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OutageService {

    private final OutageRepository outageRepository;
    private final OutageEventProducer outageEventProducer;
    private final TechnicianClient technicianClient;
    private final AIRecommendationClient aiRecommendationClient;

    public OutageService(
            OutageRepository outageRepository,
            OutageEventProducer outageEventProducer,
            TechnicianClient technicianClient,
            AIRecommendationClient aiRecommendationClient) {

        this.outageRepository = outageRepository;
        this.outageEventProducer = outageEventProducer;
        this.technicianClient = technicianClient;
        this.aiRecommendationClient = aiRecommendationClient;
    }

    public OutageResponse createOutage(Outage outage) {

        if (outage.getStatus() == null) {
            outage.setStatus(OutageStatus.REPORTED);
        }

        if (outage.getReportedAt() == null) {
            outage.setReportedAt(LocalDateTime.now());
        }

        Outage savedOutage = outageRepository.save(outage);

     
        Map<String, String> aiRequest = Map.of(
                "title", savedOutage.getTitle(),
                "description", savedOutage.getDescription(),
                "location", savedOutage.getLocation(),
                "severity", savedOutage.getSeverity().toString()
        );

        String recommendation =
                aiRecommendationClient.getRecommendation(aiRequest);

        System.out.println("AI Recommendation:");
        System.out.println(recommendation);

        outageEventProducer.sendOutageEvent(
                "OUTAGE_CREATED: " + savedOutage.getTitle()
        );
        OutageResponse response = new OutageResponse();

        response.setId(savedOutage.getId());
        response.setTitle(savedOutage.getTitle());
        response.setDescription(savedOutage.getDescription());
        response.setLocation(savedOutage.getLocation());
        response.setSeverity(savedOutage.getSeverity().toString());
        response.setStatus(savedOutage.getStatus().toString());
        response.setTechnicianId(savedOutage.getTechnicianId());
        response.setReportedAt(savedOutage.getReportedAt().toString());
        response.setAiRecommendation(recommendation);

        return response;
    }

    public List<Outage> getAllOutages() {
        return outageRepository.findAll();
    }

    public List<Outage> getOutagesByTechnician(Long technicianId) {
        return outageRepository.findByTechnicianId(technicianId);
    }
    public Outage getOutageById(Long id) {

        return outageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Outage not found"));
    }

    public Outage updateOutage(Long id, Outage updatedOutage) {

        Outage existingOutage = outageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Outage not found"));

        existingOutage.setTitle(updatedOutage.getTitle());
        existingOutage.setDescription(updatedOutage.getDescription());
        existingOutage.setLocation(updatedOutage.getLocation());
        existingOutage.setSeverity(updatedOutage.getSeverity());
        existingOutage.setStatus(updatedOutage.getStatus());

        return outageRepository.save(existingOutage);
    }

    public void deleteOutage(Long id) {

        Outage existingOutage = outageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Outage not found"));

        outageRepository.delete(existingOutage);
    }

    @CircuitBreaker(
            name = "technicianService",
            fallbackMethod = "technicianFallback"
    )
    public String getAvailableTechnicians() {

        return technicianClient.getAvailableTechnicians();
    }

    public String technicianFallback(Throwable e) {

        return "Technician Service is currently unavailable. Please try again later.";
    }

    public Outage assignTechnician(Long outageId, Long technicianId) {

        Outage outage = outageRepository.findById(outageId)
                .orElseThrow(() -> new RuntimeException("Outage not found"));

        TechnicianResponse technician =
                technicianClient.getTechnicianById(technicianId);

        if (!technician.isAvailable()) {
            throw new RuntimeException("Technician is not available");
        }

        outage.setTechnicianId(technicianId);

        Outage savedOutage = outageRepository.save(outage);

        technicianClient.markUnavailable(technicianId);

        return savedOutage;
    }

    public Outage completeOutage(Long outageId) {

        Outage outage = outageRepository.findById(outageId)
                .orElseThrow(() -> new RuntimeException("Outage not found"));

        outage.setStatus(OutageStatus.RESOLVED);

        Outage savedOutage = outageRepository.save(outage);

        if (savedOutage.getTechnicianId() != null) {

            technicianClient.markAvailable(
                    savedOutage.getTechnicianId()
            );
        }

        return savedOutage;
    }

    public Outage investigateOutage(Long outageId) {

        Outage outage = outageRepository.findById(outageId)
                .orElseThrow(() -> new RuntimeException("Outage not found"));

        outage.setStatus(OutageStatus.INVESTIGATING);

        return outageRepository.save(outage);
    }

    public Outage startProgress(Long outageId) {

        Outage outage = outageRepository.findById(outageId)
                .orElseThrow(() -> new RuntimeException("Outage not found"));

        outage.setStatus(OutageStatus.IN_PROGRESS);

        return outageRepository.save(outage);
    }
}