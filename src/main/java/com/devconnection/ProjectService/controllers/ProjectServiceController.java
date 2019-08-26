package com.devconnection.ProjectService.controllers;

import com.devconnection.ProjectService.domain.Project;
import com.devconnection.ProjectService.services.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProjectServiceController {

    private ProjectService projectService;

    @Autowired
    public ProjectServiceController(ProjectService projectService) {
        this.projectService = projectService;
    }

    

}
