package com.iprody.source.code.flow.manager.data.access.repository;

import com.iprody.source.code.flow.manager.data.access.entity.GitAdministrator;
import org.springframework.data.repository.CrudRepository;

/**
 * Class represents repository for saving and searching GitAdministrators.
 */
public interface GitAdministratorRepository extends CrudRepository<GitAdministrator, Long> {
    /**
     * Find GitAdministrator by firstname.
     * @param firstName is firstname of the Administrator.
     * @return GitAdministrator.
     */
    GitAdministrator findByFirstName(String firstName);
    /**
     * Find GitAdministrator by email.
     * @param email is email of the Administrator.
     * @return GitAdministrator.
     */
    GitAdministrator findByEmail(String email);
}
