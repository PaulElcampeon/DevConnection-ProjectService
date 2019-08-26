package com.devconnection.ProjectService.messages;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeleteProjectMessage {

    private String email;
    private String projectTitle;
}
