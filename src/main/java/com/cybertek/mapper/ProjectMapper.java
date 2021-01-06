package com.cybertek.mapper;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.entity.Project;
import com.cybertek.repo.ProjectRepo;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ProjectMapper {
    private ModelMapper modelMapper;

 private ProjectRepo projectRepo;

    public ProjectMapper(ModelMapper modelMapper, ProjectRepo projectRepo) {
        this.modelMapper = modelMapper;
        this.projectRepo = projectRepo;
    }
    public Project convertTooEntity(ProjectDTO dto){
        return modelMapper.map(dto,Project.class);
    }
    public ProjectDTO convertToDtoProjectEntity(Project project){
        return modelMapper.map(project,ProjectDTO.class);
    }

}
