package com.unipi.msc.smartalertapi.Controller;

import com.unipi.msc.smartalertapi.Interface.IStatistics;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("statistics")
public class StatisticsController {
    private final IStatistics iStatistics;
    @GetMapping("disaster")
    public ResponseEntity<?> getDisasterStatistics(@RequestParam(required = false) List<Long> disasterIds,
                                                   @RequestParam(required = false) Long dateFrom,
                                                   @RequestParam(required = false) Long dateTo){
        return iStatistics.getDisasterStatistics(disasterIds, dateFrom, dateTo);
    }
}
