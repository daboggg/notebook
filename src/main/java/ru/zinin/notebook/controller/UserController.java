package ru.zinin.notebook.controller;

import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.zinin.notebook.exception.EmailAlreadyExist;
import ru.zinin.notebook.exception.UserNotFound;
import ru.zinin.notebook.exception.WrongPassword;
import ru.zinin.notebook.model.User;
import ru.zinin.notebook.model.Views;
import ru.zinin.notebook.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @JsonView(Views.UsernameToken.class)
    @CrossOrigin(methods = RequestMethod.POST)
    public ResponseEntity<User> register(@RequestBody User user) throws EmailAlreadyExist {
        return userService.register(user);
    }

    @PostMapping("/login")
    @JsonView(Views.UsernameToken.class)
    @CrossOrigin(methods = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user) throws UserNotFound, WrongPassword {

        return userService.login(user);
    }
}
