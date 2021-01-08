package com.cybertek.converter;

import com.cybertek.dto.TaskDTO;
import com.cybertek.service.TaskService;
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
@ConfigurationPropertiesBinding
public class TaskDtoConv implements Converter<String, TaskDTO> {
  private TaskService taskService;

    public TaskDtoConv(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public TaskDTO convert(String s) {
        Long id = Long.parseLong(s);
        return taskService.findById(id);
    }
}
