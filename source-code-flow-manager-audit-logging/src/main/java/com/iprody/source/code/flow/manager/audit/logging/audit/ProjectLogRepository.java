package com.iprody.source.code.flow.manager.audit.logging.audit;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectLogRepository extends CrudRepository<ProjectAuditLog, Long> {
}
