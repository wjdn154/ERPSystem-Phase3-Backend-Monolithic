package com.megazone.ERPSystem_phase3_Monolithic.common.controller;

import com.megazone.ERPSystem_phase3_Monolithic.common.config.SecretManagerConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SecretController {

    private final SecretManagerConfig secretManagerConfig;

    @GetMapping("/secret")
    public String getSecret() {

        secretManagerConfig.getSecret();
        return "aws 시크릿매니저 연동성공스!!!";
    }
}
