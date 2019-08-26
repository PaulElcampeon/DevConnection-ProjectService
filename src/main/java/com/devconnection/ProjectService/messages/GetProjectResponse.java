package com.devconnection.ProjectService.messages;

import com.devconnection.ProjectService.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectResponse {

    private Project project;
}
