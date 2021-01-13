package com.cybertek.implementations;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.dto.TaskDTO;
import com.cybertek.dto.UserDTO;
import com.cybertek.entity.User;
import com.cybertek.exception.TicketingProjectException;
import com.cybertek.mapper.MapperUtil;
import com.cybertek.mapper.UserMapper;
import com.cybertek.repo.UserRepo;
import com.cybertek.service.ProjectService;
import com.cybertek.service.TaskService;
import com.cybertek.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

   private UserRepo userRepo;
    private  ProjectService projectService;
    private TaskService taskService;
    private MapperUtil mapperUtil;

    public UserServiceImpl(UserRepo userRepo, @Lazy ProjectService projectService, TaskService taskService, MapperUtil mapperUtil) {
        this.userRepo = userRepo;
        this.projectService = projectService;
        this.taskService = taskService;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        List <User>list = userRepo.findAll(Sort.by("firstName"));
        //convert to DTO
        return list.stream().map(obj ->{return mapperUtil.convert(obj,new UserDTO());}).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String userNam) {
        User user= userRepo.findByUserName(userNam);
        return mapperUtil.convert(user,new UserDTO());
    }

    @Override
    public void save(UserDTO dto) {
User user = mapperUtil.convert(dto,new User());
userRepo.save(user);

    }

    @Override
    public UserDTO update(UserDTO dto) {
        //Find current user
        User user = userRepo.findByUserName(dto.getUserName());
        //Map update user DTO to eentity object
        User convertedUser = mapperUtil.convert(dto,new User());
        //set id to the converted obj
        convertedUser.setId(user.getId());
        //save updated user
        userRepo.save(convertedUser);
        return findByUserName(dto.getUserName());
    }

    @Override
    public void delete(String userNam) throws TicketingProjectException {
User user= userRepo.findByUserName(userNam);
if (user==null){
    throw new TicketingProjectException("User does not exist");
}
if (!checkIfUserCanBeDeleted(user)){
    throw new TicketingProjectException("User can not be deleted. It is linked by a project or task");
}
user.setUserName(user.getUserName()+"-"+user.getId());

user.setIsDeleted(true);
userRepo.save(user);

    }

    @Override
    public void deleteByUserName(String userName) {
        userRepo.deleteByUserName(userName);
    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User>user= userRepo.findAllByRoleDescriptionIgnoreCase(role);

        return user.stream().map(obj->{return mapperUtil.convert(obj,new UserDTO());}).collect(Collectors.toList());
    }

    @Override
    public Boolean checkIfUserCanBeDeleted(User user) {
        switch (user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO>projects=projectService.readAllByManager(user);
                return projects.size()==0;
            case "Employee":
                List<TaskDTO>employee=taskService.readAllByEmployee(user);
                return employee.size()==0;
            default:
                return true;
        }

    }
}
