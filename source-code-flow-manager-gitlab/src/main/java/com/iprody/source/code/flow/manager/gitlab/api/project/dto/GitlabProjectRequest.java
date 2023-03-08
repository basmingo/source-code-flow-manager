package com.iprody.source.code.flow.manager.gitlab.api.project.dto;

import com.iprody.source.code.flow.manager.core.project.GitProjectRequest;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * It's a DTO request that contains the data needed to create a project in Gitlab.
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Schema(description = "Initial data to create a project")
public class GitlabProjectRequest implements GitProjectRequest {

    /**
     * Name of the project.
     */
    @NotBlank(message = "Project name is mandatory")
    @Size(max = 30, message = "Project Name must be less than 30 characters")
    private String name;
    /**
     * Description for the project.
     */
    @NotBlank(message = "Project description is mandatory")
    @Size(max = 140, message = "Description must be less than 140 characters")
    private String description;
    /**
     * Namespace of the project.
     * It provides one place in Gitlab to organize some related to each other projects
     */
    @NotBlank(message = "Namespace of project is mandatory")
    @Size(max = 50, message = "Namespace must be less than 50 characters")
    private String namespace;
    /**
     * Owner ID is an ID of a GitAdministrator.
     */
    @NotNull(message = "Owner ID of project is mandatory")
    @Positive(message = "Owner ID of project must be positive number")
    private long ownerId;
}
