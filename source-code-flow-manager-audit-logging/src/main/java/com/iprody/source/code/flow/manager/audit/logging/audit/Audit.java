package com.iprody.source.code.flow.manager.audit.logging.audit;

import com.iprody.source.code.flow.manager.data.access.entity.GitAdministrator;
import com.iprody.source.code.flow.manager.data.access.entity.GitProject;
import com.iprody.source.code.flow.manager.data.access.entity.GitProvider;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Map;

/**
 * This class represents an audit object that contains the necessary information
 * to perform an audit on a Git project.
 */
@Getter
@Setter
public class Audit implements Serializable {
    /**
     * A map of parameters associated with the audit.
     */
    private Map<String, Object> parameters;
    /**
     * The Git administrator responsible for executing the audit.
     */
    private GitAdministrator executor;
    /**
     * The Git provider that hosts the project being audited.
     */
    private GitProvider provider;
    /**
     * The Git project being audited.
     */
    private GitProject project;
}
