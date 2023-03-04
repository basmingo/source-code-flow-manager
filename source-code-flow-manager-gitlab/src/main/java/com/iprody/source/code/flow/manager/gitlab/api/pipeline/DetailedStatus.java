package com.iprody.source.code.flow.manager.gitlab.api.pipeline;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DetailedStatus {

    /**
     * Favicon of detailed status.
     */
    private String favicon;

    /**
     * Icon of detailed status.
     */
    private String icon;

    /**
     * Detailed status tooltip.
     */
    private String tooltip;

    /**
     * Detailed status description.
     */
    private String text;

    /**
     * Detailed status label.
     */
    private String label;

    /**
     * Detailed status group.
     */
    private String group;

    /**
     * Do any other details exist.
     */
    private boolean hasDetails;

    /**
     * Path for details.
     */
    private String detailsPath;
}
