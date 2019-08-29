package com.devconnection.ProjectService.controllers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.devconnection.ProjectService.ProjectServiceApplication;
import com.devconnection.ProjectService.domain.Position;
import com.devconnection.ProjectService.domain.Project;
import com.devconnection.ProjectService.messages.DeleteProjectMessage;
import com.devconnection.ProjectService.messages.GenericMessage;
import com.devconnection.ProjectService.messages.GenericResponse;
import com.devconnection.ProjectService.messages.GetProjectMessage;
import com.devconnection.ProjectService.messages.GetProjectResponse;
import com.devconnection.ProjectService.messages.GetProjectsResponse;
import com.devconnection.ProjectService.messages.UpdateProjectDescriptionMessage;
import com.devconnection.ProjectService.messages.UpdateProjectPositionAddMessage;
import com.devconnection.ProjectService.messages.UpdateProjectPositionMessage;
import com.devconnection.ProjectService.repositories.ProjectRepository;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest(classes = {ProjectServiceApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProjectControllerIT {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ProjectRepository projectRepository;

    @LocalServerPort
    private int port;

    private String baseUrl;

    private String projectTitle = "SunBiz";

    private Position position1;

    private Position position2;

    private Project project;

    @Before
    public void init() {
        baseUrl = String.format("http://localhost:%d/", port);
        List<Position> positions = new ArrayList<>();
        position1 = new Position("Java Developer", "", false);
        position2 = new Position("React", "", false);
        positions.addAll(Arrays.asList(position1, position2));
        project = new Project(projectTitle, "Rachel@live.co.uk", "Cool project", positions);
        projectRepository.insert(project);
    }

    @After
    public void tearDown() {
        projectRepository.deleteAll();
    }


    @Test
    public void createProject() {
        projectRepository.deleteAll();
        ResponseEntity<String> response = restTemplate.postForEntity(baseUrl + "create", project, String.class);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(projectRepository.existsById(projectTitle));
    }

    @Test
    public void getProject() {
        GetProjectMessage getProjectMessage = new GetProjectMessage();
        getProjectMessage.setTitle(projectTitle);

        ResponseEntity<GetProjectResponse> response = restTemplate.postForEntity(baseUrl + "get/single", getProjectMessage, GetProjectResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(project, response.getBody().getProject());
    }

    @Test
    public void getProjects() {
        Project newProject = new Project("Elder Scrolls", "Rachel@live.co.uk", "Cool project", Arrays.asList());
        projectRepository.insert(newProject);

        GenericMessage message = new GenericMessage();
        message.setEmail(newProject.getOwner());

        ResponseEntity<GetProjectsResponse> response = restTemplate.postForEntity(baseUrl + "get/multiple", message, GetProjectsResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().getProjects().size());
        assertTrue(response.getBody().getProjects().containsAll(Arrays.asList(newProject, project)));
    }

    @Test
    public void updateDescription() {
        UpdateProjectDescriptionMessage message = new UpdateProjectDescriptionMessage();
        message.setProjectTitle(projectTitle);
        message.setEmail(project.getOwner());
        message.setDescription("No No");

        ResponseEntity<GenericResponse> response = restTemplate.postForEntity(baseUrl + "update/description", message, GenericResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals("No No", projectRepository.findById(projectTitle).get().getDescription());
    }

    @Test
    public void updatePositionModify() {
        Position position = new Position();
        position.setFilled(true);
        position.setName("Marcus");
        position.setPosition("React");

        UpdateProjectPositionMessage message = new UpdateProjectPositionMessage();
        message.setProjectTitle(projectTitle);
        message.setEmail(project.getOwner());
        message.setPosition(position);
        message.setIndex(1);

        ResponseEntity<GenericResponse> response = restTemplate.postForEntity(baseUrl + "update/position/modify", message, GenericResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(2, projectRepository.findById(projectTitle).get().getPositions().size());
        assertEquals(position, projectRepository.findById(projectTitle).get().getPositions().get(1));
    }

    @Test
    public void updatePositionRemove() {
        Position position = new Position();
        position.setPosition("React");

        UpdateProjectPositionMessage message = new UpdateProjectPositionMessage();
        message.setProjectTitle(projectTitle);
        message.setEmail(project.getOwner());
        message.setPosition(position);
        message.setIndex(1);

        ResponseEntity<GenericResponse> response = restTemplate.postForEntity(baseUrl + "update/position/remove", message, GenericResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(1, projectRepository.findById(projectTitle).get().getPositions().size());
        assertEquals(project.getPositions().get(0), projectRepository.findById(projectTitle).get().getPositions().get(0));
    }

    @Test
    public void updatePositionAdd() {
        Position position = new Position();
        position.setPosition("React");

        UpdateProjectPositionAddMessage message = new UpdateProjectPositionAddMessage();
        message.setProjectTitle(projectTitle);
        message.setEmail(project.getOwner());
        message.setPosition(position);

        ResponseEntity<GenericResponse> response = restTemplate.postForEntity(baseUrl + "update/position/add", message, GenericResponse.class);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().isSuccess());
        assertEquals(3, projectRepository.findById(projectTitle).get().getPositions().size());
        assertTrue(projectRepository.findById(projectTitle).get().getPositions().containsAll(Arrays.asList(project.getPositions().get(0), project.getPositions().get(1), position)));
    }

    @Test
    public void deleteProject() {
        DeleteProjectMessage message = new DeleteProjectMessage();
        message.setProjectTitle(projectTitle);
        message.setEmail(project.getOwner());

        restTemplate.postForEntity(baseUrl + "delete", message, String.class);

        assertFalse(projectRepository.existsById(projectTitle));
    }
}
