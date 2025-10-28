package com.example.EMS_backend.controller;

import com.example.EMS_backend.dto.UserDTO;
import com.example.EMS_backend.service.UserService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/api/ems/users")
public class UserController {
    @Autowired
    UserService userService;

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @GetMapping("")
    public List<UserDTO> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("/{id}")
    public UserDTO getAUser(@PathVariable("id") int userId)
    {
        return userService.getAUser(userId);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @PostMapping("/add")
    public UserDTO addUser(@RequestBody UserDTO newUser)
    {
        return userService.addUser(newUser);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @PutMapping("/update")
    public UserDTO updateUser(@RequestBody UserDTO editUser)
    {
        return userService.updateUser(editUser);
    }


    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @DeleteMapping("/delete/{id}")
    public void deleteUser(@PathVariable("id") int userId)
    {
        userService.deleteUser(userId);
    }


    @PostMapping("/auth/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody UserDTO credentials) {
        String userName = credentials.getUserName();
        String password = credentials.getPassword();

        Map<String, Object> responseData = userService.login(userName, password);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


    @PostMapping("/auth/register")
    public ResponseEntity<Map<String,Object>> register(@RequestBody UserDTO userDTO)
    {
        return new ResponseEntity<>(userService.register(userDTO),HttpStatus.OK);
    }


}
