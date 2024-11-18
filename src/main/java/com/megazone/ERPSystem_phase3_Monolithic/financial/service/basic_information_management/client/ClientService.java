package com.megazone.ERPSystem_phase3_Monolithic.financial.service.basic_information_management.client;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.dto.AccountSubjectDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.account_subject.dto.AccountSubjectsAndMemosDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.Liquor;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.dto.CategoryDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.dto.ClientDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.dto.LiquorDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.company.dto.CompanyDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.common.dto.BankDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public interface ClientService {
    Long saveClient(ClientDTO clientDTO);
    ClientDTO updateClient(ClientDTO clientDTO);
    ResponseEntity<Object> fetchClientList();
    ResponseEntity<Object> searchClientList();
    ResponseEntity<Object> fetchClient(Long id);
    List<LiquorDTO> fetchLiquorList();
    List<CategoryDTO> fetchCategoryList();
    List<BankDTO> fetchBankList();
}