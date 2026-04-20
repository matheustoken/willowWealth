package org.willonwealth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.willonwealth.model.AccreditationAuditLog;

public interface AuditLogRepository extends JpaRepository<AccreditationAuditLog, Long> {
}
