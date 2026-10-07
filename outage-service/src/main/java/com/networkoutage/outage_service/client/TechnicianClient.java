package com.networkoutage.outage_service.client;



import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name = "Technician-service")
public interface TechnicianClient {

	@GetMapping("/technicians/available")
	String getAvailableTechnicians();
	
	@GetMapping("/technicians/{id}")
	TechnicianResponse getTechnicianById(@PathVariable Long id);
	
	@PutMapping("/technicians/{id}/unavailable")
	TechnicianResponse markUnavailable(@PathVariable Long id);
	
	@PutMapping("/technicians/{id}/available")
	TechnicianResponse markAvailable(@PathVariable Long id);
}