package com.iprody.source.code.flow.manager.gitlab.api.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Class represents a member and its vital parts on inner API side.
 */
@Getter
@Setter
public class Member {
    /**
     * ID of the member.
     */
    private int id;
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
     * The path to the avatar of the member.
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;
    /**
     * The path to the member.
     */
    @JsonProperty("web_url")
    private String webUrl;
    /**
     * Time when the member was added to the project.
     */
    @JsonProperty("created_at")
    private String createdAt;
    /**
     * Time when the access of the member expire.
     */
    @JsonProperty("expires_at")
    private String expiresAt;
    /**
     * A valid access level of the member.
     */
    @JsonProperty("access_level")
    private int accessLevel;
    /**
     * Email of the member.
     */
    private String email;
    /**
     * User who gave access.
     */
    @JsonProperty("created_by")
    private CreatedBy createdBy;
}
