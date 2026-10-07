package com.networkoutage.outage_service.Repository;




import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.networkoutage.outage_service.entity.Outage;

public interface OutageRepository extends JpaRepository<Outage, Long> {

    List<Outage> findByTechnicianId(Long technicianId);
}