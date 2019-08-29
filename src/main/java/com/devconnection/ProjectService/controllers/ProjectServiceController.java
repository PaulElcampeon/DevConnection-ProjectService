package com.devconnection.ProjectService.controllers;

import com.devconnection.ProjectService.domain.Project;
import com.devconnection.ProjectService.messages.*;
import com.devconnection.ProjectService.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectServiceController {

    private ProjectService projectService;

    @Autowired
    public ProjectServiceController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public void createProject(@RequestBody Project project) {
        projectService.createProject(project);
    }

    @RequestMapping(value = "/get/single", method = RequestMethod.POST)
    public GetProjectResponse getProject(@RequestBody GetProjectMessage getProjectMessage) {
        return projectService.getProject(getProjectMessage);
    }

    @RequestMapping(value = "/get/multiple", method = RequestMethod.POST)
    public GetProjectsResponse getProjects(@RequestBody GenericMessage message) {
        return projectService.getProjects(message);
    }

    @RequestMapping(value = "/update/description", method = RequestMethod.POST)
    public GenericResponse updateDescription(@RequestBody UpdateProjectDescriptionMessage updateProjectDescriptionMessage) {
        return new GenericResponse(projectService.updateProjectDescription(updateProjectDescriptionMessage));
    }

    @RequestMapping(value = "/update/position/modify", method = RequestMethod.POST)
    public GenericResponse updatePositionModify(@RequestBody UpdateProjectPositionMessage updateProjectPositionMessage) {
        return new GenericResponse(projectService.updateProjectPositionModify(updateProjectPositionMessage));
    }

    @RequestMapping(value = "/update/position/remove", method = RequestMethod.POST)
    public GenericResponse updatePositionRemove(@RequestBody UpdateProjectPositionMessage updateProjectPositionMessage) {
        return new GenericResponse(projectService.updateProjectPositionRemove(updateProjectPositionMessage));
    }

    @RequestMapping(value = "/update/position/add", method = RequestMethod.POST)
    public GenericResponse updatePositionAdd(@RequestBody UpdateProjectPositionAddMessage updateProjectPositionAddMessage) {
        return new GenericResponse(projectService.updateProjectPositionAdd(updateProjectPositionAddMessage));
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void deleteProject(@RequestBody DeleteProjectMessage deleteProjectMessage) {
       projectService.deleteProject(deleteProjectMessage);
    }
}
