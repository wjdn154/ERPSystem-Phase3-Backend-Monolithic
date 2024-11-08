package com.megazone.ERPSystem_phase3_Monolithic.hr.repository.requirement_management.JobPosting;

import com.megazone.ERPSystem_phase3_Monolithic.hr.model.requirement_management.jobposting.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobPostingRepository extends JpaRepository<JobPosting, Long> , JobPostingRepositoryCustom {
    List<JobPosting> findAll(); // 모든 채용 공고를 조회
}
