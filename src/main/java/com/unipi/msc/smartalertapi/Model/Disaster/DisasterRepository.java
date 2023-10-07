package com.unipi.msc.smartalertapi.Model.Disaster;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DisasterRepository extends JpaRepository<Disaster,Long> {
    Optional<Disaster> findById(Long id);
    List<Disaster> findAllByIdIn(List<Long> ids);
}