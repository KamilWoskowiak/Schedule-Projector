package com.scheduleProjector.scheduleProjector.controller;

import com.scheduleProjector.scheduleProjector.service.SortService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sort")
public class SortController {

    private final SortService sortService;

    public SortController(SortService sortService) {
        this.sortService = sortService;
    }

    @PostMapping("/overload")
    public ResponseEntity<?> sortOverload(
            @RequestBody List<String> courseNames,
            Authentication authentication
    ) {


        return null;
    }

    @PostMapping("/spread")
    public ResponseEntity<?> sortSpread(
            @RequestBody List<String> courseNames,
            Authentication authentication
    ) {


        return null;
    }

}
