package com.megazone.ERPSystem_phase3_Monolithic.hr.model.attendance_management.dto;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.attendance_management.Attendance;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttendanceEntryDTO {
    private Long employeeId;
    private LocalDate date;
    private LocalDateTime checkInTime;
    private LocalDateTime checkOutTime;
    private String status;

    public static AttendanceEntryDTO create(Attendance attendance){
        return new AttendanceEntryDTO(
                attendance.getEmployee().getId(),
                attendance.getDate(),
                attendance.getCheckInTime(),
                attendance.getCheckOutTime(),
                attendance.getStatus() != null ? attendance.getStatus().toString() : "AUTO" // 기본값 처리
        );
    }


}
