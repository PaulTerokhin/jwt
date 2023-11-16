package com.softserve.itacademy.todolist.controller;

import com.softserve.itacademy.todolist.dto.UserResponse;
import com.softserve.itacademy.todolist.service.UserService;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class HomeController {
    private final UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "home"})
    public List<UserResponse> home(Model model) {
        return userService.getAll().stream()
                .map(UserResponse::new)
                .collect(Collectors.toList());
    }
}
