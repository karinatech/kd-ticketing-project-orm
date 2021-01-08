package com.cybertek.implementations;

import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Task;
import com.cybertek.enums.Status;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.repo.TaskRepo;
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

    public TaskServiceImpl(TaskRepo taskRepo,TaskMapper taskMapper) {
        this.taskRepo = taskRepo;
        this.taskMapper=taskMapper;
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
}
