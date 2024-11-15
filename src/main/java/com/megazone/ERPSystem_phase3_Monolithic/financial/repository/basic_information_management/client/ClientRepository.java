package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.basic_information_management.client;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.Category;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.Client;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.dto.fetchClientListDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.dto.searchClientListDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long>, ClientRepositoryCustom {

    Optional<Client> findByCode(String code);

    @Query("SELECT MAX(c.code) FROM client c")
    String findMaxCode();

    @Query("SELECT new com.megazone.ERPSystem_phase3_Monolithic.financial.model.basic_information_management.client.dto.searchClientListDTO(c.id, c.printClientName) " +
            "FROM client c")
    List<searchClientListDTO> searchClientList();
}