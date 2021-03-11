package com.cybertek.implementations;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.Project;
import com.cybertek.entity.User;
import com.cybertek.enums.Status;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repo.ProjectRepo;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {
    private ProjectMapper projectMapper;
    private ProjectRepo projectRepo;
    private UserMapper userMapper;
    private UserService userService;
    private TaskService taskService;

    public ProjectServiceImpl(ProjectMapper projectMapper, ProjectRepo projectRepo, UserMapper userMapper, UserService userService, TaskService taskService) {
        this.projectMapper = projectMapper;
        this.projectRepo = projectRepo;
        this.userMapper = userMapper;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Override
    public ProjectDTO getByProjectCode(String code) {
      Project project=  projectRepo.findByProjectCode(code);

        return projectMapper.convertToDtoProjectEntity(project);

    }


    @Override
    public List<ProjectDTO> listAllProjects() {
        List<Project>projects = projectRepo.findAll(Sort.by("projectCode"));
        return projects.stream().map(obj ->{return projectMapper.convertToDtoProjectEntity(obj);})
                .collect(Collectors.toList()) ;
    }

    @Override
    public Project save(ProjectDTO dto) {
        dto.setProjectStatus(Status.OPEN);
        Project obj = projectMapper.convertTooEntity(dto);
        obj.setAssignedManager(userMapper.convertToEntity(dto.getAssignedManager()));
        Project project = projectRepo.save(obj);
        return project;
    }

    @Override
    public void update(ProjectDTO dto) {
        Project project=projectRepo.findByProjectCode(dto.getProjectCode());
        Project converted = projectMapper.convertTooEntity(dto);
        converted.setId(project.getId());
        converted.setProjectStatus(project.getProjectStatus());
        projectRepo.save(converted);

    }

    @Override
    public void delete(String code) {
Project project=projectRepo.findByProjectCode(code);
project.setIsDeleted(true);
project.setProjectCode(project.getProjectCode()+"-"+project.getId());
projectRepo.save(project);
taskService.deleteByProject(projectMapper.convertToDtoProjectEntity(project));

    }

    @Override
    public void complete(String project) {
        Project obj=projectRepo.findByProjectCode(project);
        obj.setProjectStatus(Status.COMPLETE);
        projectRepo.save(obj);
    }

    @Override
    public List<ProjectDTO> listAllProjectDetails() {
        UserDTO currentUserDto= userService.findByUserName("admin.com");
        User user=userMapper.convertToEntity(currentUserDto);
        List<Project>list = projectRepo.findByAssignedManager(user);

        return list.stream().map(project -> {ProjectDTO obj =projectMapper.convertToDtoProjectEntity((project));
        obj.setUnfinishedTaskCount(taskService.totalNonCompletedTasks(project.getProjectCode()));
        obj.setCompleteTaskCount(taskService.totalCompleetedTasks(project.getProjectCode()));
        return obj;}).collect(Collectors.toList());
    }

    @Override
    public List<ProjectDTO> readAllByManager(User user) {
        List<Project>projects=projectRepo.findByAssignedManager(user);
        return projects.stream().map(projectMapper::convertToDtoProjectEntity).collect(Collectors.toList());

    }
}
