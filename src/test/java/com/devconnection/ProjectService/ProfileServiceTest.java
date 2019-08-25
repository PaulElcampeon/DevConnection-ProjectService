package com.devconnection.ProjectService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static com.sun.javaws.JnlpxArgs.verify;
import static org.mockito.Mockito.times;

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

        verify(profileRepository,times(1)).insert(Mockito.any(createProfileMessage));
    }

}
