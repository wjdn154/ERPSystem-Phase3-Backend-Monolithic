package com.megazone.ERPSystem_phase3_Monolithic.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.SchemaBasedMultiTenantConnectionProvider;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.TenantService;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.security.*;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.dto.*;
import com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.Permission;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.Permission.PermissionRepository;
import com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase3_Monolithic.hr.service.basic_information_management.Users.UsersService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.hibernate.tool.schema.internal.SchemaCreatorImpl;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.sql.SQLException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/hr")
public class UsersController {


    private final UsersService usersService;
    private final UsersRepository usersRepository;
    private final PermissionRepository permissionRepository;
    private final CompanyRepository companyRepository;


    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final SchemaBasedMultiTenantConnectionProvider multiTenantConnectionProvider;
    private final PlatformTransactionManager transactionManager;

    @PostMapping("/auth/google-login")
    public ResponseEntity<Object> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            // 요청 데이터에서 인증 코드와 회사 ID 추출
            Long companyId = request.getCompanyId();
            String code = request.getCode();

            System.out.println("Company ID: " + companyId);
            System.out.println("구글 인증 Code: " + code);

            if (code == null || code.trim().isEmpty()) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증 코드가 없습니다.");
            }

            if (companyId == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("회사 ID가 없습니다.");
            }

            // 1. 구글에 Access Token 요청
            String tokenEndpoint = "https://oauth2.googleapis.com/token";
            String clientId = "189901934577-ir0en4b9eqe4j6ehb6imcou61t2ec9mn.apps.googleusercontent.com";
            String clientSecret = "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";
            String redirectUri = "http://localhost:3000/callback";

            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> params = new HashMap<>();
            params.put("grant_type", "authorization_code");
            params.put("client_id", clientId);
            params.put("client_secret", clientSecret);
            params.put("redirect_uri", redirectUri);
            params.put("code", code);

            //access token 응답
            Map<String, Object> tokenResponse = restTemplate.postForObject(tokenEndpoint, params, Map.class);

            if (tokenResponse == null || !tokenResponse.containsKey("access_token")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("구글 인증 실패");
            }

            String accessToken = (String) tokenResponse.get("access_token");

            // 2. 구글 사용자 정보 요청
            String userInfoEndpoint = "https://www.googleapis.com/oauth2/v2/userinfo";
            //요청 url (access Token 포함)
            String userInfoUrl = UriComponentsBuilder.fromHttpUrl(userInfoEndpoint)
                    .queryParam("access_token", accessToken)
                    .toUriString();

            //구글 사용자정보 응답
            Map<String, Object> userInfo = restTemplate.getForObject(userInfoUrl, Map.class);

            if (userInfo == null || !userInfo.containsKey("email")) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("사용자 정보를 가져올 수 없습니다.");
            }

            String email = (String) userInfo.get("email");
            String name = (String) userInfo.get("name");

            System.out.println("구글 email : "+ email);
            System.out.println("구글 name : "+ name);

            // 테넌트 식별자 설정
            String tenantId = "tenant_" + companyId;

            // 테넌트 컨텍스트 설정
            TenantContext.setCurrentTenant(tenantId);

            // 사용자 정보 가져오기 . 신규 가입자면 db에 새로 저장
            Users user = usersRepository.findByUserName(email).orElseGet(() -> {
                try{
                    // 회사 선택 검증
                    if (request.getCompanyId() == null) {
                        throw new IllegalArgumentException("회사를 선택해주세요.");
                    }
                    System.out.println("신규 사용자, 등록 진행: " + email);

                    // 테넌트 식별자 설정
                    TenantContext.setCurrentTenant("tenant_" + request.getCompanyId());

                    // 사용자 등록
                    ResponseEntity<String> tenantResponse = usersService.googleRegisterUser(email, name, companyId);

                    // 등록한 사용자를 다시 조회
                    return usersRepository.findByUserName(email)
                            .orElseThrow(() -> new RuntimeException("사용자 등록 후 조회 실패"));

                } catch (Exception e){
                    throw new RuntimeException("구글 로그인 신규 사용자 등록 중 오류 발생 : "+e.getMessage(), e);
                }
            });

            // 사용자 인증
            ResponseEntity<Object> authentication = usersService.googleCreateAuthentication(email,name, tenantId, companyId);

            if (authentication.getStatusCode() != HttpStatus.OK) return authentication;

            // 사용자 권한 조회
            ResponseEntity<Object> permissionByUsername = usersService.getPermissionByUsername(email);
            if (permissionByUsername.getStatusCode() != HttpStatus.OK) return authentication;

            Company company = companyRepository.findById(companyId).orElse(null);
            if (company == null) return ResponseEntity.badRequest().body("회사를 찾을 수 없습니다.");

            boolean isAdmin = company.getAdminUsername().equals(email);

            // 3. Cognito에 토큰 발급 요청
            String cognitoTokenEndpoint = "https://ap-northeast-2217t3ejhc.auth.ap-northeast-2.amazoncognito.com/oauth2/token";
            String cognitoClientId = "2vbjbe8baqj88bmckgvag8klmt";
            String cognitoClientSecret = "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";

            HttpHeaders cognitoHeaders = new HttpHeaders();
            cognitoHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

            //MultiValueMap으로 요청 데이터 설정
            MultiValueMap<String, String> cognitoParams = new LinkedMultiValueMap<>();
            cognitoParams.add("grant_type", "authorization_code");
            cognitoParams.add("client_id", cognitoClientId);
            cognitoParams.add("client_secret", cognitoClientSecret);
            cognitoParams.add("scope", "email openid profile");

            //사용자 지정 속성 추가(필요한 경우). user pool에서 custom 속성 설정 확인
            cognitoParams.add("custom:X-Tenant-ID", tenantId);
            cognitoParams.add("custom:companyId", String.valueOf(companyId));
            cognitoParams.add("custom:email", email);
            cognitoParams.add("custom:name", user.getUserNickname());
            cognitoParams.add("custom:employeeId", String.valueOf(user.getEmployee().getId()));
            cognitoParams.add("custom:permissionId", String.valueOf(user.getPermission().getId()));

            HttpEntity<MultiValueMap<String, String>> cognitoRequest = new HttpEntity<>(cognitoParams, cognitoHeaders);

            try {
                // Cognito 요청 실행
                ResponseEntity<Map> cognitoResponseEntity = restTemplate.postForEntity(cognitoTokenEndpoint, cognitoRequest, Map.class);

                if (!cognitoResponseEntity.getStatusCode().is2xxSuccessful()) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cognito 토큰 발급 실패: " + cognitoResponseEntity.getStatusCode());
                }

                // 응답 처리
                Map<String, Object> cognitoResponse = cognitoResponseEntity.getBody();
                String cognitoAccessToken = (String) cognitoResponse.get("access_token");
                String cognitoRefreshToken = (String) cognitoResponse.get("refresh_token");

                if (cognitoAccessToken == null || cognitoRefreshToken == null) {
                    return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Cognito 토큰 발급 실패: 액세스 또는 리프레시 토큰이 없습니다.");
                }

                // 최종 응답 데이터
                Map<String, Object> response = new HashMap<>();
                response.put("cognitoAccessToken", cognitoAccessToken);
                response.put("cognitoRefreshToken", cognitoRefreshToken);
                response.put("permission", permissionByUsername.getBody());
                response.put("isAdmin", isAdmin);

                // 테넌트 컨텍스트 해제
                TenantContext.clear();

                return ResponseEntity.ok(response);

            } catch (HttpClientErrorException e) {
                System.err.println("Cognito 요청 오류: " + e.getResponseBodyAsString());
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Cognito 요청 중 오류 발생: " + e.getMessage());
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("구글 로그인 처리 중 오류 발생: " + e.getMessage());
        }
    }


    // 로그인
    @PostMapping("/auth/login")
    public ResponseEntity<Object> createAuthenticationToken(@RequestBody AuthRequest authRequest) {

        // 회사 선택 검증
        if(authRequest.getCompanyId() == null) return ResponseEntity.badRequest().body("회사를 선택해주세요.");

        // 테넌트 식별자 설정
        String tenantId = "tenant_" + authRequest.getCompanyId();

        // 테넌트 컨텍스트 설정
        TenantContext.setCurrentTenant(tenantId);

        // 사용자 인증 & 토큰 생성
        ResponseEntity<Object> authenticationToken = usersService.createAuthenticationToken(authRequest, tenantId);
        if (authenticationToken.getStatusCode() != HttpStatus.OK) return authenticationToken;

        // 사용자 권한 조회
        ResponseEntity<Object> permissionByUsername = usersService.getPermissionByUsername(authRequest.getUserName());
        if (permissionByUsername.getStatusCode() != HttpStatus.OK) return authenticationToken;

        Company company = companyRepository.findById(authRequest.getCompanyId()).orElse(null);
        if (company == null) return ResponseEntity.badRequest().body("회사를 찾을 수 없습니다.");
        boolean isAdmin = company.getAdminUsername().equals(authRequest.getUserName());

        String refreshToken = jwtUtil.generateRefreshToken(authRequest.getUserName());

        Map<Object, Object> response = new HashMap<>();
        response.put("token", authenticationToken.getBody());
        response.put("refreshToken", refreshToken);
        response.put("permission", permissionByUsername.getBody());
        response.put("isAdmin", isAdmin);


        // 테넌트 컨텍스트 해제
        TenantContext.clear();

        return ResponseEntity.ok(response);
    }

    // 리프레시 토큰
    @PostMapping("/auth/refresh-token")
    public ResponseEntity<Object> refreshAuthenticationToken(@RequestBody Map<String, String> refreshTokenRequest) {
        return usersService.createRefreshToken(refreshTokenRequest);
    }

    // 회원가입
    @PostMapping("/auth/register")
    public ResponseEntity<String> registerUser(@RequestBody AuthRequest authRequest) throws SQLException {

        // 회사 선택 검증
        if(authRequest.getCompanyId() == null) return ResponseEntity.badRequest().body("회사를 선택해주세요.");

        // 테넌트 식별자 설정
        TenantContext.setCurrentTenant("tenant_" + authRequest.getCompanyId());

        // 사용자 등록
        ResponseEntity<String> tenantResponse = usersService.registerUser(authRequest);

        // 테넌트 컨텍스트 해제
        TenantContext.clear();

        return tenantResponse;
    }

    // 사용자 권한 조회
    @PostMapping("/users/permission/{username}")
    public ResponseEntity<Object> getPermissionByUsername(@PathVariable("username") String username) {
        return usersService.getPermissionByUsername(username);
    }

    // 사용자 권한 수정
    @PostMapping("/users/permission/update")
    public ResponseEntity<Object> assignPermissionToUser(@RequestBody UserPermissionUpdateRequestDTO request) {
        return usersService.assignPermissionToUser(
                request.getUsername(),
                request.getPermissionDTO()
        );
    }











//    @PostMapping("/{userId}/assign-permission/{permissionId}")
//    public ResponseEntity<UsersPermissionDTO> assignPermissionToUser(@PathVariable Long userId, @PathVariable Long permissionId) {
//        UsersPermissionDTO usersPermissionDTO = usersService.assignPermissionToUser(userId, permissionId);
//        return usersPermissionDTO != null ? ResponseEntity.ok(usersPermissionDTO) : ResponseEntity.notFound().build();
//    }

    /**
     * 모든 사용자 정보를 가져옴.
     *
     * @return 모든 사용자 정보를 담은 리스트를 반환함.
     */
    @PostMapping("/users/all")
    public ResponseEntity<List<UsersShowDTO>> getAllUsers() {
        List<UsersShowDTO> users = usersService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    /**
     * ID로 특정 사용자를 조회함.
     *
     * @param id 조회할 사용자의 ID
     * @return 조회된 사용자 정보를 반환함.
     */
    @PostMapping("/users/{id}")
    public ResponseEntity<UsersShowDTO> getUserById(@PathVariable("id") Long id) {
        UsersShowDTO user = usersService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    /**
     * 사용자를 수정함.
     *
     * @param id       수정할 사용자의 ID
     * @param usersDTO 수정할 사용자 정보
     * @return 수정된 사용자 정보를 반환함.
     */
    @PutMapping("/users/put/{id}")
    public ResponseEntity<UsersShowDTO> updateUser(@PathVariable("id") Long id, @RequestBody UsersShowDTO usersDTO) {
        UsersShowDTO updatedUser = usersService.updateUser(id, usersDTO);
        return ResponseEntity.ok(updatedUser);
    } // 다시 해야 됨

    /**
     * ID로 사용자를 삭제함.
     *
     * @param id 삭제할 사용자의 ID
     * @return 삭제 성공 메시지를 반환함.
     */
    @DeleteMapping("/users/del/{id}")
    public ResponseEntity<String> deleteUsers(@PathVariable("id") Long id) {
        usersService.deleteUsers(id);
        return ResponseEntity.ok("사용자 삭제되었습니다.");
    }
}

