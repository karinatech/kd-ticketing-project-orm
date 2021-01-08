package com.cybertek.controller;

import com.cybertek.dto.ProjectDTO;
import com.cybertek.service.ProjectService;
import com.cybertek.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/project")
public class ProjectController {

    ProjectService projectService;
    UserService userService;

    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @GetMapping("/create")
    public String createProject(Model model) {
        model.addAttribute("project", new ProjectDTO());
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("managers", userService.listAllByRole("Manager"));
        return "/project/create";
    }

    @PostMapping("/create")
    public String insertProject(ProjectDTO project) {
        projectService.save(project);
//        project.setProjectStatus(Status.OPEN);
        return "redirect:/project/create";
    }

    @GetMapping("/delete/{projectcode}")
    public String deleteProject(@PathVariable("projectcode") String projectcode) {
        projectService.delete(projectcode);
        return "redirect:/project/create";
    }

    @GetMapping("/complete/{projectcode}")
    public String completeProject(@PathVariable("projectcode") String projectcode) {
        projectService.complete(projectcode);
        return "redirect:/project/create";
    }

    @GetMapping("/update/{projectcode}")
    public String editProject(@PathVariable("projectcode") String projectcode, Model model) {

        model.addAttribute("project", projectService.getByProjectCode(projectcode));
        model.addAttribute("projects", projectService.listAllProjects());
        model.addAttribute("managers", userService.listAllByRole("Manager"));

        return "/project/update";
    }

    @PostMapping("/update/{projectCode}")
    public String updateProject(@PathVariable("projectCode") String projectCode, ProjectDTO project) {
        projectService.update(project);
        return "redirect:/project/create";
    }
//
//    @GetMapping("/manager/complete")
//    public String getProjectsByManager(Model model) {
//        UserDTO manager = userService.findById("john@cybertek.com");
//
//        List<ProjectDTO> projects = projectService.findAll().stream().filter(project -> project.getAssignedManager().equals(manager)).collect(Collectors.toList());
//        model.addAttribute("projects", projects);
//
//        return "/manager/project-status";
//    }
//
//    List<ProjectDTO> getCountedListOfProjectDTO(UserDTO manager) {
//        List<ProjectDTO> list = projectService.findAll().stream().filter(project -> project.getAssignedManager()
//                .equals(manager))
//                .map(project -> {
//                    List<TaskDTO> taskDTOList = taskService.findTasksByManagers(manager);
//                    int completeCount = (int) taskDTOList.stream().filter(t -> t.getProject().equals(project) && t.getTaskStatus() == Status.COMPLETE).count();
//                    int inCompleteCount = (int) taskDTOList.stream().filter(t -> t.getProject().equals(project) && t.getTaskStatus() != Status.COMPLETE).count();
//                    project.setCompleteTaskCount(completeCount);
//                    project.setUnfinishedTaskCount(inCompleteCount);
//                    return project;
//
//                }).collect(Collectors.toList());
//        return list;
//    }
}
