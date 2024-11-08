package com.megazone.ERPSystem_phase3_Monolithic.financial.repository.common;

import com.megazone.ERPSystem_phase3_Monolithic.financial.model.common.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContactRepository extends JpaRepository<Contact, Long> {
}