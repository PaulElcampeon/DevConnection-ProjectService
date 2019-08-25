package com.devconnection.ProjectService.services;

import com.devconnection.ProjectService.domain.Profile;
import com.devconnection.ProjectService.messages.*;
import com.devconnection.ProjectService.repositories.ProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProfileServiceImpl implements ProfileService {

    private ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public void createProfile(CreateProfileMessage createProfileMessage) {
        profileRepository.insert(new Profile(createProfileMessage.getId()));
    }

    @Override
    public Profile getProfile(String id) {
        return null;
    }

    @Override
    public void updateProfileDescription(UpdateProfileDescriptionMessage updateProfileDescriptionMessage) {

    }

    @Override
    public void updateProfileCurrentProjects(UpdateProfileCurrentProjectsMessage updateProfileCurrentProjects) {

    }

    @Override
    public void updateProfileExperience(UpdateProfileExperienceMessage updateProfileExperienceMessage) {

    }

    @Override
    public void updateProfileSkills(UpdateProfileSkillsMessage updateProfileSkillsMessage) {

    }

    @Override
    public void removeProfile(String id) {

    }
}
