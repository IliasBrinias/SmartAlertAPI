package com.unipi.msc.smartalertapi.Model.Risk;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RiskRepository extends JpaRepository<Risk,Long> {
    Optional<Risk> findById(Long id);
}