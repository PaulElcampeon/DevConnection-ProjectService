package com.devconnection.ProjectService;

import com.devconnection.ProjectService.domain.Profile;
import com.devconnection.ProjectService.messages.CreateProfileMessage;
import com.devconnection.ProjectService.repositories.ProfileRepository;
import com.devconnection.ProjectService.services.ProfileServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class ProfileServiceTest {

    @Mock
    private ProfileRepository profileRepository;

    @InjectMocks
    private ProfileServiceImpl profileService;


    @Test
    public void createProfile() {
        String emailId = "Dannny@live.com";
        CreateProfileMessage createProfileMessage = new CreateProfileMessage(emailId);
        profileService.createProfile(createProfileMessage);

        verify(profileRepository,times(1)).insert(Mockito.any((Profile.class)));
    }

}
