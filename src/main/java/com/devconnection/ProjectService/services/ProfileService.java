package com.devconnection.ProjectService.services;

import com.devconnection.ProjectService.domain.Profile;
import com.devconnection.ProjectService.messages.*;

public interface ProfileService {

    void createProfile(CreateProfileMessage createProfileMessage);

    Profile getProfile(String id);

    void updateProfileDescription(UpdateProfileDescriptionMessage updateProfileDescriptionMessage);

    void updateProfileCurrentProjects(UpdateProfileCurrentProjectsMessage updateProfileCurrentProjects);

    void updateProfileExperience(UpdateProfileExperienceMessage updateProfileExperienceMessage);

    void updateProfileSkills(UpdateProfileSkillsMessage updateProfileSkillsMessage);

    void removeProfile(String id);

}
