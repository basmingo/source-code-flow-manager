package com.iprody.source.code.flow.manager.audit.logging.audit;

import com.iprody.source.code.flow.manager.data.access.entity.GitProject;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * This class represents a log of an audit performed on a Git project.
 */
@Entity
@Table(name = "project_audit_log")
@Getter
@Setter
public class ProjectAuditLog {

    /**
     * ID of the audit log.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * The Git project that was audited.
     */
    @ManyToOne
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private GitProject projectId;
    /**
     * The audit information associated with this log.
     */
    @Column(name = "audit", columnDefinition = "jsonb")
    private Audit audit;
}
