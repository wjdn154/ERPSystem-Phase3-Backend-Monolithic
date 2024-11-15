package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.AccountSubject;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.dto.AccountSubjectSearchDTO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// JpaRepository와 커스텀 리포지토리를 확장하는 인터페이스
public interface AccountSubjectRepository extends JpaRepository<AccountSubject, Long>, AccountSubjectRepositoryCustom {

    /**
     * 코드로 계정과목을 조회함.
     * @param code 계정과목 코드
     * @return 해당 코드의 계정과목을 Optional로 반환함.
     */
    Optional<AccountSubject> findByCode(String code);

    @Query("SELECT a FROM account_subject a WHERE a.name LIKE %:name% OR a.code LIKE %:code% ORDER BY LENGTH(a.code), a.code ASC")
    List<AccountSubject> findByNameOrCodeContainingOrderByCodeAsc(@Param("name") String name, @Param("code") String code);

    @Query("SELECT new com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.dto.AccountSubjectSearchDTO(a.code, a.name) " +
            "FROM account_subject a ORDER BY LENGTH(a.code), a.code ASC")
    List<AccountSubjectSearchDTO> findAllByOrderByCodeAsc();
}