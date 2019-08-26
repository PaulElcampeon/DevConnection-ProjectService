package com.devconnection.ProjectService.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Project {

    @Id
    private String title;
    private String owner;
    private String description;
    private List<Position> positions = new ArrayList<>();
}
