package com.cybertek.controller;

import com.cybertek.dto.UserDTO;
import com.cybertek.exception.TicketingProjectException;
import com.cybertek.service.RoleService;
import com.cybertek.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    RoleService roleService;
    @Autowired
    UserService userService;

    @GetMapping("/create")
    public String createUser(Model model){
        model.addAttribute("user",new UserDTO());
        model.addAttribute("roles",roleService.liistAllllRoles());
        model.addAttribute("users",userService.listAllUsers());
        return "/user/create";
    }
    @PostMapping("/create")
    public String insertUser(UserDTO user,Model model){
        userService.save(user);
        return "redirect:/user/create";
    }
    @GetMapping("/update/{userName}")
    public String editUser(@PathVariable("userName") String userName, Model model){
        model.addAttribute("user",userService.findByUserName(userName));
        model.addAttribute("roles",roleService.liistAllllRoles());
        model.addAttribute("users",userService.listAllUsers());
        return "/user/update";
    }

    @PostMapping("/update/{userName}")
    public String updateUser(@PathVariable("userName") String userName, UserDTO user,Model model){
        userService.update(user);
        return "redirect:/user/create";
    }
    @GetMapping("/delete/{username}")
    public String deleteUser(@PathVariable("username") String username) throws TicketingProjectException {
        userService.delete(username);
        return "redirect:/user/create";
    }
}
