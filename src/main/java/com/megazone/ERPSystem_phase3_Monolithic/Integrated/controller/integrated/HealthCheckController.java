package com.megazone.ERPSystem_phase3_Monolithic.Integrated.controller.integrated;


import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class HealthCheckController {

    @GetMapping("/")
    public String healthCheck() {
        return "healthChecking!!";
    }
}
