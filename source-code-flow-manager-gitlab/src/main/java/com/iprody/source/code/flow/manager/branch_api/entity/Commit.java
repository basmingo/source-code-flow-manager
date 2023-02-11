package com.iprody.source.code.flow.manager.branch_api.entity;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
public class Commit {
    private String authorName;
    private String authoredDate;
    private String committerEmail;
    private String createdAt;
    private String shortId;
    private List<Object> parentIds;
    private String title;
    private String message;
    private String committerName;
    private String committedDate;
    private String webUrl;
    private String authorEmail;
    private String id;
}