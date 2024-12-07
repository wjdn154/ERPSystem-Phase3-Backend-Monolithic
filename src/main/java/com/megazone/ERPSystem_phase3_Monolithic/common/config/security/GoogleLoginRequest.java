package com.megazone.ERPSystem_phase3_Monolithic.common.config.security;

import lombok.Data;

@Data
public class GoogleLoginRequest {

    private String googleIdToken;
    private Long companyId;
}
