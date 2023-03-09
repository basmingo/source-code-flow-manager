package com.iprody.source.code.flow.manager.core.member;

import lombok.Getter;
import lombok.Setter;

import java.net.URI;
import java.time.Instant;

/**
 * Class represents a git member of the project and its vital parts
 * in a vendor related implementation (e.g. Gitlab, Github).
 */
@Getter
@Setter
public class GitMember {

    /**
     * ID of the member.
     */
    private long id;
    /**
     * Username of the member.
     */
    private String username;
    /**
     * Name of the member.
     */
    private String name;
    /**
     * State of the member.
     */
    private String state;
    /**
     * The path to the member.
     */
    private URI webUrl;
    /**
     * Time when the member was added to the project.
     */
    private Instant createdAt;
    /**
     * Time when the access of the member expire.
     */
    private Instant expiresAt;
    /**
     * A valid access level of the member.
     */
    private int accessLevel;
    /**
     * Email of the member.
     */
    private String email;
}
