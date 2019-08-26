package com.devconnection.ProjectService.messages;

import com.devconnection.ProjectService.domain.Project;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetProjectsResponse {

    private List<Project> projects = new ArrayList<>();
}
