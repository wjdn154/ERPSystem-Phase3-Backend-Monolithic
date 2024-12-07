package com.megazone.ERPSystem_phase3_Monolithic.common.config.security;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GoogleLoginRequest {

    private Long companyId;
    private String code;
}
