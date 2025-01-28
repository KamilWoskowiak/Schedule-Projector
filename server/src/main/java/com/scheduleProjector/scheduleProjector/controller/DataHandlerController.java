package com.scheduleProjector.scheduleProjector.controller;

import com.scheduleProjector.scheduleProjector.dto.BulkDataDto;
import com.scheduleProjector.scheduleProjector.model.User;
import com.scheduleProjector.scheduleProjector.service.DataHandlerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/data")
public class DataHandlerController {

    private final DataHandlerService dataHandlerService;

    public DataHandlerController(DataHandlerService dataHandlerService) {
        this.dataHandlerService = dataHandlerService;
    }

    @PostMapping()
    public ResponseEntity<?> handleData(
            @RequestBody BulkDataDto data,
            Authentication authentication
    ) {
        String email = (String) authentication.getPrincipal();
        try {
            dataHandlerService.handleData(data, email);
            return ResponseEntity.ok("Courses & semesters added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<DataHandlerController> getData(
            Authentication authentication
    ) {
        String email = (String) authentication.getPrincipal();
        return ResponseEntity.ok(this);
    }


}
