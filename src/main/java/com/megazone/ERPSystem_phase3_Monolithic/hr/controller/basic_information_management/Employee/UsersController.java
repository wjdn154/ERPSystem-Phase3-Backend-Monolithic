package com.megazone.ERPSystem_phase3_Monolithic.hr.controller.basic_information_management.Employee;

import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.SchemaBasedMultiTenantConnectionProvider;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.TenantContext;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.multi_tenant.TenantService;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.security.AuthRequest;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.security.CustomUserDetails;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.security.GoogleLoginRequest;
import com.megazone.ERPSystem_phase3_Monolithic.common.config.security.JwtUtil;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.Company;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.dto.*;
import com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.company.CompanyRepository;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.Employee;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.Permission;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.Users;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.basic_information_management.employee.dto.*;
import com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.Employee.EmployeeRepository;
import com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.Permission.PermissionRepository;
import com.megazone.ERPSystem_phase3_Monolithic.hr.repository.basic_information_management.Users.UsersRepository;
import com.megazone.ERPSystem_phase3_Monolithic.hr.service.basic_information_management.Users.UsersService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.bind.annotation.*;
import org.hibernate.tool.schema.internal.SchemaCreatorImpl;
import org.springframework.web.server.ResponseStatusException;

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
    private final EmployeeRepository employeeRepository;

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;
    private final SchemaBasedMultiTenantConnectionProvider multiTenantConnectionProvider;
    private final PlatformTransactionManager transactionManager;



    // 구글 로그인 (토큰 생성 없이 정보만 반환)
    @PostMapping("/auth/google-login")
    public ResponseEntity<Object> googleLogin(@RequestBody GoogleLoginRequest request) {
        try {
            // 1. 구글 ID 토큰 검증 및 이메일 추출
            String email = jwtUtil.verifyGoogleIdToken(request.getGoogleIdToken());
            if (email == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 구글 토큰입니다.");
            }

            System.out.println("Received googleIdToken: " + request.getGoogleIdToken());
            System.out.println("Decoded email from token: " + email);
            // 2. 사용자 정보 가져오기 (이메일로 조회)
            Users user = usersRepository.findByUserName(email).orElse(null);

            // 3. 사용자 생성 (필요시)
            if (user == null) {
                Employee employee = employeeRepository.findByEmail(email)
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "사원 정보를 찾을 수 없습니다."));

                user = new Users();
                user.setUserName(email);
                user.setUserNickname(employee.getFirstName() + " " + employee.getLastName());
                user.setEmployee(employee);
                user.setCompany(companyRepository.findById(request.getCompanyId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회사 정보를 찾을 수 없습니다.")));
                user.setPermission(new Permission()); // 기본 권한 설정
                System.out.println("Retrieved company: " + request.getCompanyId());
                usersRepository.save(user);
            }

            // 4. 회사 정보 검증
            Company company = companyRepository.findById(request.getCompanyId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "회사를 찾을 수 없습니다."));

            // 5. 응답 생성 (JWT 없이 필요한 정보만 반환)
            Map<Object, Object> response = new HashMap<>();
            response.put("email", user.getUserName());
            response.put("nickname", user.getUserNickname());
            response.put("companyId", user.getCompany().getId());
            response.put("employeeId", user.getEmployee().getId());
            response.put("permission", user.getPermission());
            response.put("isAdmin", company.getAdminUsername().equals(user.getUserName()));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("구글 로그인 처리 중 오류 발생");
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

