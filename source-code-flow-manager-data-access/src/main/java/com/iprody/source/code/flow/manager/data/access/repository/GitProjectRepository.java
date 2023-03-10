package com.iprody.source.code.flow.manager.data.access.repository;

import com.iprody.source.code.flow.manager.data.access.entity.GitProject;
import org.springframework.data.repository.CrudRepository;

/**
 * Class represents repository for saving and searching Project.
 */
public interface GitProjectRepository extends CrudRepository<GitProject, Long> {
    /**
     * Find Project by GitAdministrator id.
     * @param gitAdministratorId is id of Administrator.
     * @return Project.
     */
    GitProject findByGitAdministratorId(long gitAdministratorId);
    /**
     * Find Project by GitProvider id.
     * @param gitProviderId is id of Provider.
     * @return Project.
     */
    GitProject findByGitProviderId(long gitProviderId);
}
