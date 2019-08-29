package com.devconnection.ProjectService.services;

import com.devconnection.ProjectService.ProjectServiceApplication;
import com.devconnection.ProjectService.domain.Position;
import com.devconnection.ProjectService.domain.Project;
import com.devconnection.ProjectService.messages.*;
import com.devconnection.ProjectService.repositories.ProjectRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@SpringBootTest(classes = {ProjectServiceApplication.class})
public class ProjectServiceIT {

    @Autowired
    private ProjectService projectService;

    @Autowired
    private ProjectRepository projectRepository;

    @Before
    public void init() {
        projectRepository.deleteAll();
    }

    @Test
    public void createProject() {
        List<Position> positions = new ArrayList<>();
        positions.add(new Position("Java Developer", "Dave", false));
        Project project = new Project("SunBiz", "Dan@livec.o.uk", "awesome project", positions);

        projectService.createProject(project);

        assertTrue(projectRepository.existsById("SunBiz"));
    }

    @Test
    public void getProjects() {
        Project project1 = new Project();
        project1.setTitle("Sunbiz");
        project1.setOwner("Dave");

        Project project2 = new Project();
        project2.setTitle("RainBiz");
        project2.setOwner("Dave");

        projectRepository.insert(Arrays.asList(project1, project2));

        GenericMessage message = new GenericMessage("Dave");

        GetProjectsResponse getProjectsResponse = projectService.getProjects(message);

        assertEquals(2, getProjectsResponse.getProjects().size());
    }

    @Test
    public void updateProjectDescription() {
        Project project = new Project();
        project.setTitle("Sunbiz");
        project.setOwner("Dave@live.com");
        project.setDescription("I know right");

        projectRepository.insert(project);

        UpdateProjectDescriptionMessage message = new UpdateProjectDescriptionMessage();
        message.setDescription("Hello");
        message.setEmail("Dave@live.com");
        message.setProjectTitle("Sunbiz");

        boolean result = projectService.updateProjectDescription(message);

        Project retrievedProject = projectRepository.findById("Sunbiz").get();

        assertTrue(result);
        assertEquals("Hello", retrievedProject.getDescription());
    }

    @Test
    public void updateProjectPositionModify() {
        String projectTitle = "SunBiz";
        List<Position> positions = new ArrayList<>();
        Position position1 = new Position("Java Developer", "Dave", true);
        Position position2 = new Position("React", "Felix", true);
        positions.add(position1);
        positions.add(position2);
        Project project = new Project(projectTitle, "Dan@livec.o.uk", "awesome project", positions);

        projectRepository.insert(project);

        UpdateProjectPositionMessage message = new UpdateProjectPositionMessage();
        message.setIndex(0);
        message.setPosition(new Position("Java Developer", "Dave", false));
        message.setProjectTitle(projectTitle);

        boolean result = projectService.updateProjectPositionModify(message);

        Project retrievedProject = projectRepository.findById(projectTitle).get();

        assertEquals(message.getPosition(), retrievedProject.getPositions().get(0));
        assertTrue(result);
    }

    @Test
    public void updateProjectPositionRemove() {
        String projectTitle = "SunBiz";
        List<Position> positions = new ArrayList<>();
        Position position1 = new Position("Java Developer", "Dave", true);
        Position position2 = new Position("React", "Felix", true);
        positions.add(position1);
        positions.add(position2);
        Project project = new Project(projectTitle, "Dan@livec.o.uk", "awesome project", positions);

        projectRepository.insert(project);

        UpdateProjectPositionMessage message = new UpdateProjectPositionMessage();
        message.setIndex(0);
        message.setPosition(position1);
        message.setProjectTitle(projectTitle);

        boolean result = projectService.updateProjectPositionRemove(message);

        Project retrievedProject = projectRepository.findById(projectTitle).get();

        assertEquals(1, retrievedProject.getPositions().size());

        assertTrue(result);
    }

    @Test
    public void updateProjectPositionAdd() {
        String projectTitle = "SunBiz";
        List<Position> positions = new ArrayList<>();
        Position position1 = new Position("Java Developer", "Dave", true);
        Position position2 = new Position("React", "Felix", true);
        positions.add(position1);
        positions.add(position2);
        Project project = new Project(projectTitle, "Dan@livec.o.uk", "awesome project", positions);

        projectRepository.insert(project);

        UpdateProjectPositionAddMessage message = new UpdateProjectPositionAddMessage();
        message.setPosition(new Position("Vue js", "", false));
        message.setProjectTitle(projectTitle);

        boolean result = projectService.updateProjectPositionAdd(message);

        Project retrievedProject = projectRepository.findById(projectTitle).get();

        assertEquals(3, retrievedProject.getPositions().size());

        assertTrue(result);
    }

    @Test
    public void deleteProject() {
        Project project = new Project();
        project.setTitle("Sunbiz");
        project.setOwner("Dave");

        projectRepository.insert(project);

        projectRepository.deleteById("Sunbiz");

        assertFalse(projectRepository.existsById("Sunbiz"));
    }
}
