package com.iprody.source.code.flow.manager.data.access.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

/**
 * Class represents entity of GitProvider.
 */
@Entity
@Table(name = "git_providers")
@Getter
@Setter
public class GitProvider {
    /**
     * ID of the GitProvider.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * Name of the GitProvider.
     */
    @Column(length = 30, unique = true)
    private String name;
}
