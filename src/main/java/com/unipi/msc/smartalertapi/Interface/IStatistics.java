package com.unipi.msc.smartalertapi.Interface;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IStatistics {
    ResponseEntity<?> getDisasterStatistics(List<Long> disasterIds, Long dateFrom, Long dateTo);
}
