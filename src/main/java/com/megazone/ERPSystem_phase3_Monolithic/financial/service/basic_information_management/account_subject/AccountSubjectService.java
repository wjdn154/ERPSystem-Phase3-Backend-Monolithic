package com.megazone.ERPSystem_phase3_Monolithic.financial.service.basic_information_management.account_subject;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.dto.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface AccountSubjectService {
    Optional<AccountSubjectsAndMemosDTO> findAllAccountSubjectDetails();

    Optional<Object> addMemoToAccountSubject (String accountSubjectCode, MemoRequestDTO memoRequestDTO);

    List<AccountSubjectSearchDTO> searchAccountSubject();

    Optional<AccountSubjectDTO> updateAccountSubject(String code, AccountSubjectDTO dto);

    Optional<AccountSubjectDTO> saveAccountSubject(AccountSubjectDTO dto);

    void deleteAccount(String code);

}