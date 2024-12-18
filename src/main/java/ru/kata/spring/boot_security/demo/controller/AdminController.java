package ru.kata.spring.boot_security.demo.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AdminController {

    private final UserService userService;
    private final RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/admin/")
    public ModelAndView allUsers() {
        List<User> users = userService.allUsers();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("users");
        modelAndView.addObject("userList",users);
        return modelAndView;
    }

    @GetMapping("/admin/edit")
    public ModelAndView editPage(@RequestParam("id") Long id) {
        User user = userService.getById(id);
        List<Role> allRoles = roleService.allRoles();
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("form");
        modelAndView.addObject("user",user);
        modelAndView.addObject("allRoles", allRoles);
        return modelAndView;
    }

    @PostMapping("/admin/edit")
    public ModelAndView editUser(@ModelAttribute("user") User user,
                                 @RequestParam List<Long> rolesId) {
        ModelAndView modelAndView = new ModelAndView();
        userService.edit(user, rolesId);
        modelAndView.setViewName("redirect:/admin/");
        return modelAndView;
    }

    @GetMapping("/admin/add")
    public ModelAndView addPage() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("form");
        modelAndView.addObject("user", new User());
        modelAndView.addObject("allRoles", roleService.allRoles());
        return modelAndView;
    }

    @PostMapping("/admin/add")
    public ModelAndView addUser(@ModelAttribute("user") User user,
                                @RequestParam List<Long> rolesId) {
        ModelAndView modelAndView = new ModelAndView();
        userService.add(user,rolesId);
        modelAndView.setViewName("redirect:/admin/");
        return modelAndView;
    }

    @GetMapping("/admin/delete")
    public ModelAndView deleteUser(@RequestParam("id") Long id) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("redirect:/admin/");
        User user = userService.getById(id);
        userService.delete(user);
        return modelAndView;
    }
}



