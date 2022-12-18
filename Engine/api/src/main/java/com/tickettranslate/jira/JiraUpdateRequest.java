package com.tickettranslate.jira;

public class JiraUpdateRequest {

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public JiraUpdateRequest(String description) {
        this.description = description;
    }

    private String description;

}

