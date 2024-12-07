package com.megazone.ERPSystem_phase3_Monolithic.common.config.security;

import com.nimbusds.jose.JWSObject;
import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.nimbusds.jose.crypto.ECDSAVerifier;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.ECKey;
import com.nimbusds.jwt.SignedJWT;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.Comments;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.Map;

@Component
public class CognitoJWTVerifier {

    private static final String REGION = "ap-northeast-2"; // 예: us-east-1
    private static final String USER_POOL_ID = "ap-northeast-2_217T3eJhC"; // Cognito 사용자 풀 ID
    private static final String JWKS_URL = String.format("https://cognito-idp.%s.amazonaws.com/%s/.well-known/jwks.json", REGION, USER_POOL_ID);

    public static Map<String, Object> verifyAndDecodeJWT(String token) throws Exception {
        // JWKS URL에서 공개 키 가져오기
        URL jwksUrl = new URL(JWKS_URL);
        JWKSet jwkSet = JWKSet.load(jwksUrl);

        // JWT 파서 생성
        SignedJWT signedJWT = SignedJWT.parse(token);
        String keyID = signedJWT.getHeader().getKeyID();

        // 키 ID에 해당하는 JWK 검색
        JWK jwk = jwkSet.getKeyByKeyId(keyID);
        if (jwk == null) {
            throw new IllegalArgumentException("JWK not found for Key ID: " + keyID);
        }

        // 키 타입에 따른 서명 검증
        boolean isVerified = false;
        if (jwk instanceof RSAKey) {
            // RSA 키를 사용한 서명 검증
            RSAKey rsaKey = (RSAKey) jwk;
            isVerified = signedJWT.verify(new RSASSAVerifier(rsaKey.toRSAPublicKey()));
        } else if (jwk instanceof ECKey) {
            // EC 키를 사용한 서명 검증
            ECKey ecKey = (ECKey) jwk;
            isVerified = signedJWT.verify(new ECDSAVerifier(ecKey.toECPublicKey()));
        } else {
            throw new IllegalArgumentException("Unsupported key type: " + jwk.getKeyType());
        }

        if (!isVerified) {
            throw new IllegalArgumentException("Invalid JWT signature");
        }

        // 클레임 반환
        return signedJWT.getJWTClaimsSet().getClaims();
    }
}
