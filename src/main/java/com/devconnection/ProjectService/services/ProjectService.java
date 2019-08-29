package com.devconnection.ProjectService.services;

import com.devconnection.ProjectService.domain.Project;
import com.devconnection.ProjectService.messages.*;

public interface ProjectService {

    void createProject(Project project);

    GetProjectsResponse getProjects(GenericMessage message);

    GetProjectResponse getProject(GetProjectMessage getProjectMessage);

    boolean updateProjectDescription(UpdateProjectDescriptionMessage updateProjectDescriptionMessage);

    boolean updateProjectPositionModify(UpdateProjectPositionMessage updateProjectPositionModifyMessage);

    boolean updateProjectPositionRemove(UpdateProjectPositionMessage updateProjectPositionModifyMessage);

    boolean updateProjectPositionAdd(UpdateProjectPositionAddMessage updateProjectPositionAddMessage);

    void deleteProject(DeleteProjectMessage deleteProjectMessage);
}
