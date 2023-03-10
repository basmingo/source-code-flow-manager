package com.iprody.source.code.flow.manager.data.access.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToOne;
import jakarta.persistence.JoinColumn;
import lombok.Getter;
import lombok.Setter;

/**
 * Class represents entity of GitCredential.
 */
@Entity
@Table(name = "git_credentials")
@Getter
@Setter
public class GitCredential {
    /**
     * ID of the GitCredential.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * Secret key of the GitCredential.
     */
    @Column(nullable = false)
    private String secret;
    /**
     * The GitAdministrator - owner.
     */
    @OneToOne
    @JoinColumn(name = "git_administrator_id", referencedColumnName = "id")
    private GitAdministrator gitAdministrator;
    /**
     * The GitProvider.
     */
    @OneToOne
    @JoinColumn(name = "git_provider_id", referencedColumnName = "id")
    private GitProvider gitProvider;
}
