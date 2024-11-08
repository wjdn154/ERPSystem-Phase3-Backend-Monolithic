package com.megazone.ERPSystem_phase3_Monolithic.Integrated.repository.notification;

import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.notification.Notification;
import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.notification.dto.UserNotificationDTO;
import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.notification.enums.ModuleType;
import com.megazone.ERPSystem_phase3_Monolithic.Integrated.model.notification.enums.PermissionType;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.financial_statements.dto.FinancialStatementsLedgerDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.financial_statements.dto.FinancialStatementsLedgerSearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.financial_statements.dto.IncomeStatementLedgerDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.financial_statements.dto.IncomeStatementSearchDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.ledger.dto.*;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherDeleteDTO;
import com.megazone.ERPSystem_phase3_Monolithic.financial.model.voucher_entry.general_voucher_entry.dto.ResolvedVoucherShowDTO;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface NotificationRepositoryCustom {
    List<UserNotificationDTO> fetchNotification(Long userId, ModuleType module, PermissionType permission);
}
