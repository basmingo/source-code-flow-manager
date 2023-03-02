package com.iprody.source.code.flow.manager.gitlab.api.branch;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.NonNull;

/**
 * Class represents a branch and its vital parts on inner API side.
 */
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Branch {

    /**
     * Enable default member.
     */
    private boolean jsonMemberDefault;

    /**
     * Enable protected member.
     */
    private boolean jsonMemberProtected;

    /**
     * The path where the branch is located.
     */
    private String webUrl;

    /**
     * Enable push the branch for developers.
     */
    private boolean developersCanPush;
    /**
     * Enable merge the branch for developers.
     */
    private boolean developersCanMerge;

    /**
     * Name of the branch.
     */
    @ToString.Include
    @NonNull
    private String name;

    /**
     * Represents the commit of the branch.
     */
    private Commit commit;

    /**
     * Enable merge for the branch.
     */
    private boolean merged;

    /**
     * Enable push for the branch.
     */
    private boolean canPush;
}
