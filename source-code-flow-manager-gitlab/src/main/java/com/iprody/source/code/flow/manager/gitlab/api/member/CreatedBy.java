package com.iprody.source.code.flow.manager.gitlab.api.member;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Class represents a user who gave access to the member and its vital parts on inner API side.
 */
@Getter
@Setter
public class CreatedBy {

    /**
     * ID of the user.
     */
    private long id;
    /**
     * Username of the user.
     */
    private String username;
    /**
     * Name of the user.
     */
    private String name;
    /**
     * State of the user.
     */
    private String state;
    /**
     * The path to the avatar of the user.
     */
    @JsonProperty("avatar_url")
    private String avatarUrl;
    /**
     * The path to the user.
     */
    @JsonProperty("web_url")
    private String webUrl;
}
