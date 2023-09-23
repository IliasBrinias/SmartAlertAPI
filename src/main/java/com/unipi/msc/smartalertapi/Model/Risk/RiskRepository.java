package com.unipi.msc.smartalertapi.Model.Risk;

import com.unipi.msc.smartalertapi.Model.User.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RiskRepository extends JpaRepository<Risk,Long> {
    Optional<Risk> findById(Long id);
}