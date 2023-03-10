package com.iprody.source.code.flow.manager.data.access.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.Setter;

/**
 * Class represents entity of Project.
 */
@Entity
@Table(name = "git_projects")
@Getter
@Setter
public class GitProject {
    /**
     * ID of the Project.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;
    /**
     * Name of the Project.
     */
    @Column(length = 100)
    private String name;
    /**
     * Url of the Project.
     */
    @Column(name = "web_url", unique = true, length = 255)
    private String webUrl;
    /**
     * The GitAdministrator - owner.
     */
    @ManyToOne
    @JoinColumn(name = "git_administrator_id", referencedColumnName = "id")
    private GitAdministrator gitAdministrator;

    /**
     * The GitProvider.
     */
    @OneToOne
    @JoinColumn(name = "git_provider_id", referencedColumnName = "id")
    private GitProvider gitProvider;
}
