package com.iprody.source.code.flow.manager.branch_api.entity;

import lombok.*;

@Getter
@NoArgsConstructor
@RequiredArgsConstructor
@ToString(onlyExplicitlyIncluded = true)
public class Branch {
    private boolean jsonMemberDefault;
    private boolean jsonMemberProtected;
    private String webUrl;
    private boolean developersCanPush;
    private boolean developersCanMerge;
    @ToString.Include
    @NonNull
    private String name;
    private Commit commit;
    private boolean merged;
    private boolean canPush;
}
