package com.iprody.source.code.flow.manager.data.access.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

/**
 * Class represents entity of GitAdministrator.
 */
@Entity
@Table(name = "git_administrators")
@Getter
@Setter
public class GitAdministrator {
    /**
     * ID of the GitAdministrator.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * First name of the GitAdministrator.
     */
    @Column(length = 30)
    private String firstName;
    /**
     * Last name of the GitAdministrator.
     */
    @Column(length = 30)
    private String lastName;
    /**
     * Email of the GitAdministrator.
     */
    @Column(length = 50, unique = true, nullable = false)
    private String email;
    /**
     * GitAdministrator's providers.
     */
    @OneToMany
    private Set<GitProvider> providers;
    /**
     * GitAdministrator's projects.
     */
    @OneToMany
    private Set<GitProject> projects;
}
