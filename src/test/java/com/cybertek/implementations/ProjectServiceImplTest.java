package com.cybertek.implementations;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.entity.Project;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.repo.ProjectRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)

class ProjectServiceImplTest {
    @Mock
    ProjectRepo projectRepo;
    @Mock
    ProjectMapper projectMapper;
    @InjectMocks
    ProjectServiceImpl projectService;


    @Test
    void getByProjectCode() {
        Project project=new Project();
        ProjectDTO projectDTO=new ProjectDTO();
        when(projectRepo.findByProjectCode("P111")).thenReturn(project);
        when(projectMapper.convertToDtoProjectEntity(project)).thenReturn(projectDTO);

        ProjectDTO projectDTO1= projectService.getByProjectCode("P111");
verify(projectRepo).findByProjectCode(Mockito.anyString());
verify(projectMapper).convertToDtoProjectEntity(Mockito.any(Project.class));

        assertNotNull(projectDTO1);
    }
    @Test
    public void getByProjectCode_exception(){
        when(projectRepo.findByProjectCode("")).thenThrow(new RuntimeException("Project not found"));
        Throwable exception =assertThrows(RuntimeException.class,() -> projectService.getByProjectCode(""));
        verify(projectRepo.findByProjectCode(Mockito.anyString()));
        assertEquals(exception.getMessage(),"Project not found");
    }

    @Test
    void save() {
        ProjectDTO projectDTO= new ProjectDTO();
Project project=new Project();
when(projectMapper.convertTooEntity(projectDTO)).thenReturn(project);
when(projectRepo.save(project)).thenReturn(project);
        projectService.save(projectDTO);
        verify(projectRepo.save(project));

    }
}