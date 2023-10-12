package com.codestatus.domain.report.service;

import com.codestatus.domain.user.entity.User;

public interface ReportService<T> {
    void report(T targetEntity, User user);
    void sendReportAlarm(T targetEntity);
}
