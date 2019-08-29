package com.devconnection.ProjectService.services;

import com.devconnection.ProjectService.domain.Project;
import com.devconnection.ProjectService.messages.*;
import com.devconnection.ProjectService.repositories.ProjectRepository;
import com.mongodb.client.result.UpdateResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class ProjectServiceImpl implements ProjectService {

    private ProjectRepository projectRepository;

    private MongoTemplate mongoTemplate;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository, MongoTemplate mongoTemplate) {
        this.projectRepository = projectRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createProject(Project project) {
        projectRepository.insert(project);
    }

    @Override
    public GetProjectsResponse getProjects(GenericMessage message) {
        Query query = new Query();
        query.addCriteria(Criteria.where("owner").is(message.getEmail()));

        List<Project> projects = mongoTemplate.find(query, Project.class);

        return new GetProjectsResponse(projects);
    }

    @Override
    public GetProjectResponse getProject(GetProjectMessage getProjectMessage) {
        Project project = projectRepository.findById(getProjectMessage.getTitle()).orElseThrow(NoSuchElementException::new);
        return new GetProjectResponse(project);
    }

    @Override
    public boolean updateProjectDescription(UpdateProjectDescriptionMessage updateProjectDescriptionMessage) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(updateProjectDescriptionMessage.getProjectTitle()));

        Update update = new Update();
        update.set("description", updateProjectDescriptionMessage.getDescription());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Project.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean updateProjectPositionModify(UpdateProjectPositionMessage updateProjectPositionMessage) {
        Query query = new Query();
        query.addCriteria(Criteria
                .where("_id").is(updateProjectPositionMessage.getProjectTitle())
                .and("positions.position").is(updateProjectPositionMessage.getPosition().getPosition()));

        Update update = new Update();
        update.set("positions."+updateProjectPositionMessage.getIndex()+".position", updateProjectPositionMessage.getPosition().getPosition());
        update.set("positions."+updateProjectPositionMessage.getIndex()+".name", updateProjectPositionMessage.getPosition().getName());
        update.set("positions."+updateProjectPositionMessage.getIndex()+".filled", updateProjectPositionMessage.getPosition().isFilled());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Project.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean updateProjectPositionRemove(UpdateProjectPositionMessage updateProjectPositionModifyMessage) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(updateProjectPositionModifyMessage.getProjectTitle()));

        Update update = new Update();
        update.pull("positions", updateProjectPositionModifyMessage.getPosition());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Project.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public boolean updateProjectPositionAdd(UpdateProjectPositionAddMessage updateProjectPositionAddMessage) {
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(updateProjectPositionAddMessage.getProjectTitle()));

        Update update = new Update();
        update.addToSet("positions", updateProjectPositionAddMessage.getPosition());

        UpdateResult updateResult = mongoTemplate.updateFirst(query, update, Project.class);

        return updateResult.getModifiedCount() == 1;
    }

    @Override
    public void deleteProject(DeleteProjectMessage deleteProjectMessage) {
        projectRepository.deleteById(deleteProjectMessage.getProjectTitle());
    }
}
