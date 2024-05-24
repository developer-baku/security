package org.services.resource_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DemoController {
    @GetMapping("/demon")
    public String getDemoController() {
        return "deamon's tool";
    }
}
