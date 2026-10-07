package com.networkoutage.Technician_service.repository;



import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.networkoutage.Technician_service.entities.Technician;



public interface TechnicianRepository extends JpaRepository<Technician, Long> {
	List<Technician> findByAvailableTrue();
}