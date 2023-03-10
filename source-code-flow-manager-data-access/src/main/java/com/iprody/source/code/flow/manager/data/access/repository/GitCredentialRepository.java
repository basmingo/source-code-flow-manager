package com.iprody.source.code.flow.manager.data.access.repository;

import com.iprody.source.code.flow.manager.data.access.entity.GitCredential;
import org.springframework.data.repository.CrudRepository;

/**
 * Class represents repository for saving and searching GitCredential.
 */
public interface GitCredentialRepository extends CrudRepository<GitCredential, Long> {
    /**
     * Find GitCredential by Administrator's id.
     * @param gitAdministratorId is id of the Administrator.
     * @return GitCredential.
     */
    GitCredential findByGitAdministratorId(long gitAdministratorId);
    /**
     * Find GitCredential by Provider's id.
     * @param gitProviderId is id of the Provider.
     * @return GitCredential.
     */
    GitCredential findByGitProviderId(long gitProviderId);
}
