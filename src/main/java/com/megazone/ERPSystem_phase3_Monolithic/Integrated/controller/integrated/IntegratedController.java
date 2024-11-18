package com.megazone.ERPSystem_phase3_Monolithic.Integrated.controller.integrated;

import com.megazone.ERPSystem_phase3_Monolithic.Integrated.service.dashboard.IntegratedService;
//import com.megazone.ERPSystem_phase3_Monolithic.common.config.SecretManagerConfig;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.SecretManagerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/integrated")
@RequiredArgsConstructor
public class IntegratedController {

    private final IntegratedService integratedService;

    private final SecretManagerConfig secretManagerConfig;

    @PostMapping("/dashboard")
    public ResponseEntity<Object> dashboard() {
        try {
            return ResponseEntity.status(HttpStatus.OK).body(integratedService.dashboard());
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    @GetMapping("/secret")
    public String getSecret() {

        secretManagerConfig.getSecret();
        return "AWS Secret Retrieval Success!!!";
    }

}