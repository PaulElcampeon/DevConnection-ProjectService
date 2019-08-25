package com.devconnection.ProjectService.messages;

import lombok.Data;

@Data
public class UpdateProfileCurrentProjectsMessage {

    private String id;
    private boolean leave;
}
