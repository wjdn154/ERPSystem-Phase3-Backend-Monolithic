package com.megazone.ERPSystem_phase3_Monolithic.common.config.security;

import com.nimbusds.jwt.SignedJWT;

import java.text.ParseException;
import java.util.Map;

public class JWTClaimExtractor {

    public static Map<String, Object> extractClaims(String token) throws ParseException {
        // JWT 파싱
        SignedJWT signedJWT = SignedJWT.parse(token);

        // 클레임 추출
        return signedJWT.getJWTClaimsSet().getClaims();
    }

    public static void main(String[] args) {
        String jwtToken = "<JWT_TOKEN>"; // 검증된 JWT 토큰

        try {
            // 클레임 추출
            Map<String, Object> claims = extractClaims(jwtToken);

            // 클레임 출력
            claims.forEach((key, value) -> System.out.println(key + ": " + value));
        } catch (ParseException e) {
            System.out.println("Invalid JWT token: " + e.getMessage());
        }
    }
}

