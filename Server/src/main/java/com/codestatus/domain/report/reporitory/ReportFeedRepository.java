package com.codestatus.domain.report.reporitory;

import com.codestatus.domain.report.entity.ReportFeed;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportFeedRepository extends JpaRepository<ReportFeed, Long>, ReportCustomRepository {
}
