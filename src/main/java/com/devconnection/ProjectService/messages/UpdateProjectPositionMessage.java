package com.devconnection.ProjectService.messages;

import com.devconnection.ProjectService.domain.Position;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProjectPositionMessage {

    private String email;
    private String projectTitle;
    private Position position;
    private int index;
}
