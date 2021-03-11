package com.cybertek.implementations;

import com.cybertek.dto.TaskDTO;
import com.cybertek.entity.Task;
import com.cybertek.mapper.TaskMapper;
import com.cybertek.repo.TaskRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    TaskRepo taskRepo;
    @Mock
    TaskMapper taskMapper;
    @InjectMocks
    TaskServiceImpl taskService;

@ParameterizedTest
@ValueSource(longs = {1L,2L,3L})
    void findById(long args) {
        Task task = new Task();
        when(taskRepo.findById(3L)).thenReturn(Optional.of(task));
        when(taskMapper.convertToDTO(task)).thenReturn(new TaskDTO());
        taskService.findById(args);
        verify(taskRepo).findById(args);
    }
    @Test
    void findById_BDDTest(){
    // given, when , then
        Task task = new Task();

        given(taskRepo.findById(Mockito.anyLong())).willReturn(Optional.of(task));
        given(taskMapper.convertToDTO(task)).willReturn(new TaskDTO());

        taskService.findById(Mockito.anyLong());

        then(taskRepo).should().findById(Mockito.anyLong());
        then(taskRepo).should(never()).findById(-5L);
}

}