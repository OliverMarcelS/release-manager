package com.litit.releasemanager.controller;


import com.litit.releasemanager.resource.ServiceResource;
import com.litit.releasemanager.service.SystemVersionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReleaseManagerRestController {

    private static final Logger log = LoggerFactory.getLogger(ReleaseManagerRestController.class);
    private final SystemVersionService systemVersionService;

    public ReleaseManagerRestController(SystemVersionService systemVersionService) {
        this.systemVersionService = systemVersionService;
    }


    @PostMapping("/deploy")
    public int deployService(@RequestBody ServiceResource service) {

        log.info("POST ReleaseManagerRestController.deploy: service: " + service);
        return systemVersionService.deployService(service);
    }

    @GetMapping("/services")
    public List<ServiceResource> getServices(@RequestParam int systemVersion) {

        log.info("GET ReleaseManagerRestController.getServices: systemVersion: " + systemVersion);
        return systemVersionService.getServicesBySystemVersion(systemVersion);
    }
}
