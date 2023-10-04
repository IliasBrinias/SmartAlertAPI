package com.unipi.msc.smartalertapi.Model.Alert;

import com.unipi.msc.smartalertapi.Model.Disaster.Disaster;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends JpaRepository<Alert,Long> {
    List<Alert> findAllByNotifiedTrueAndTimestampGreaterThanOrderByTimestampDesc(Long timestamp);
    List<Alert> findAllByTimestampGreaterThanOrderByTimestampDesc(Long timestamp);
    boolean existsByTimestampGreaterThanAndNotifiedTrueAndDisasterEquals(Long timestamp, Disaster disaster);
}