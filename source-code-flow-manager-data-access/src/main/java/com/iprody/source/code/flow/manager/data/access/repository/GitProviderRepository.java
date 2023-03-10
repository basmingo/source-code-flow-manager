package com.iprody.source.code.flow.manager.data.access.repository;

import com.iprody.source.code.flow.manager.data.access.entity.GitProvider;
import org.springframework.data.repository.CrudRepository;
/**
 * Class represents repository for saving and searching GitProvider.
 */
public interface GitProviderRepository extends CrudRepository<GitProvider, Long> {

}
