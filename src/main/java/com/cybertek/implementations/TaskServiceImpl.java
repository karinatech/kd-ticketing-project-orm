package com.cybertek.implementations;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Task;
import com.cybertek.entity.User;
import com.cybertek.enums.Status;
import com.cybertek.mapper.ProjectMapper;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.repo.TaskRepo;
import com.cybertek.repo.UserRepo;
import com.cybertek.service.TaskService;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {
    TaskRepo taskRepo;
    TaskMapper taskMapper;
    ProjectMapper projectMapper;
    UserRepo userRepo;

    public TaskServiceImpl(TaskRepo taskRepo, TaskMapper taskMapper, ProjectMapper projectMapper, UserRepo userRepo) {
        this.taskRepo = taskRepo;
        this.taskMapper = taskMapper;
        this.projectMapper = projectMapper;
        this.userRepo = userRepo;
    }

    @Override
    public TaskDTO findById(Long id) {
        Optional<Task>task= taskRepo.findById(id);
        if(task.isPresent()){
            return taskMapper.convertToDTO(task.get());
        }
        return null;
    }

    @Override
    public List<TaskDTO> listAllTasks() {
        List<Task>task =taskRepo.findAll();
        return task.stream().map(obj->{return taskMapper.convertToDTO(obj);}).collect(Collectors.toList());

    }

    @Override
    public Task save(TaskDTO dto) {
        dto.setTaskStatus(Status.OPEN);
        dto.setAssignedDate(LocalDate.now());
        Task task = taskMapper.convertToEntity(dto);
        return taskRepo.save(task);
    }

    @Override
    public void update(TaskDTO dto) {
        Optional<Task>task= taskRepo.findById(dto.getId());
        Task conveerted = taskMapper.convertToEntity(dto);
        if (task.isPresent()){
            conveerted.setId(task.get().getId());
            conveerted.setTaskStatus(task.get().getTaskStatus());
            conveerted.setAssignedDate(task.get().getAssignedDate());
            taskRepo.save(conveerted);
        }

    }

    @Override
    public void delete(Long id) {
Optional<Task> foundTask= taskRepo.findById(id);
if(foundTask.isPresent()){
    foundTask.get().setIsDeleted(true);
    taskRepo.save(foundTask.get());
}

    }

    @Override
    public int totalNonCompletedTasks(String projectCode) {

        return taskRepo.totalnonCompleteTasks(projectCode);
    }

    @Override
    public int totalCompleetedTasks(String projectCode) {
        return taskRepo.totalCompletedTasks(projectCode);
    }

    @Override
    public void deleteByProject(ProjectDTO dto) {

      List<TaskDTO>list = listAllTasksByProject(dto);
      list.forEach(task ->delete(task.getId()));
    }

    @Override
    public List<TaskDTO> listAllTasksByProject(ProjectDTO dto) {
        List<Task>list =taskRepo.findAllByProject(projectMapper.convertTooEntity(dto));
        return list.stream().map(obj->{return taskMapper.convertToDTO(obj);}).collect(Collectors.toList());

    }

    @Override
    public List<TaskDTO> listAllTasksByStatusIsNot(Status status) {
        User user = userRepo.findByUserName("employee.com");
        List<Task>list = taskRepo.findAllByTaskStatusIsNotAndAssignedEmployee(status,user);

        return list.stream().map(taskMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public List<TaskDTO> listAllTasksByProjectManager() {
        User user=userRepo.findByUserName("admin.com");
        List<Task>tasks = taskRepo.findAllByProjectAssignedManager(user);

        return tasks.stream().map(taskMapper::convertToDTO).collect(Collectors.toList());

    }
}
