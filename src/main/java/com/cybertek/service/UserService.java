package com.cybertek.service;

import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;

import java.util.List;

public interface UserService {
    List<UserDTO>listAllUsers();
    UserDTO findByUserName(String userNam);

    void save(UserDTO dto);
    UserDTO update(UserDTO dto);
    void delete(String userNam);
    void deleteByUserName(String userName);
List<UserDTO>listAllByRole(String role);


}
