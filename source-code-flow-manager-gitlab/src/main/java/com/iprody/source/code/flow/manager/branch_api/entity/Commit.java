package com.iprody.source.code.flow.manager.branch_api.entity;

import lombok.Getter;

import java.util.List;

/**
 * Class represents a git commit of branch and its vital parts on inner API side.
 */
@Getter
public class Commit {

    /**
     * Name of author the commit.
     */
    private String authorName;

    /**
     * Last time of authored.
     */
    private String authoredDate;

    /**
     * Email of committer.
     */
    private String committerEmail;

    /**
     * Date and time of the project creation.
     */
    private String createdAt;

    /**
     * ID of the commit.
     */
    private String shortId;

    /**
     * Parent ID's.
     */
    private List<Object> parentIds;

    /**
     * Title of the commit.
     */
    private String title;

    /**
     * Message of the commit.
     */
    private String message;

    /**
     * Author name of the commit.
     */
    private String committerName;

    /**
     * Last time of committed.
     */
    private String committedDate;

    /**
     * The path where the commit is located.
     */
    private String webUrl;

    /**
     * The commit author email.
     */
    private String authorEmail;

    /**
     * The ID of the commit.
     */
    private String id;
}
