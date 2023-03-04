package com.iprody.source.code.flow.manager.gitlab.api.pipeline;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class User {

    /**
     * User ID.
     */
    private int id;

    /**
     * The path where the user's avatar is located.
     */
    private String avatarUrl;

    /**
     * The path where the pipeline is located.
     */
    private String webUrl;

    /**
     * User's name.
     */
    private String name;

    /**
     * Pipeline state.
     */
    private String state;

    /**
     * User's username.
     */
    private String username;
}
