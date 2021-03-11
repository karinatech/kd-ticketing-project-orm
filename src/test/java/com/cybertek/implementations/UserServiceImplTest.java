package com.cybertek.implementations;

import com.cybertek.mapper.MapperUtil;
import com.cybertek.repo.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    UserRepo userRepo;

    @InjectMocks
    UserServiceImpl userService;

    @Mock
    MapperUtil mapperUtil;


    @Test
    void deleteByUserName() {
        userService.deleteByUserName("kari.com");


        //verification

//verify(userRepo,times(2)).deleteByUserName("kari.com");
verify(userRepo).deleteByUserName("kari.com");
    }
}