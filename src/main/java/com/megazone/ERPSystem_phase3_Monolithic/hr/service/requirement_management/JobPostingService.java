package com.megazone.ERPSystem_phase3_Monolithic.hr.service.requirement_management;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.requirement_management.jobposting.DTO.JobPostingDTO;
import com.megazone.ERPSystem_phase3_Monolithic.hr.model.requirement_management.jobposting.JobPosting;

import java.util.List;

public interface JobPostingService {
    List<JobPostingDTO> findAllJobPostings();
}
