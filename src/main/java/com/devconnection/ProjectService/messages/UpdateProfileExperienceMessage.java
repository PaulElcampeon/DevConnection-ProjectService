package com.devconnection.ProjectService.messages;

import lombok.Data;

@Data
public class UpdateProfileExperienceMessage {

    private String id;
    private int yearsExperience;
}
