package com.fightclub.user_service.controllers;

import com.fightclub.user_service.entities.UserDTO;
import com.fightclub.user_service.entities.UserEntity;
import com.fightclub.user_service.mapper.UserMapper;
import com.fightclub.user_service.services.AppUserService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/users")
@RequiredArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final UserMapper userMapper;


    @GetMapping("/")
    public List<UserDTO> getUsers() {
        return appUserService.getUsers().stream().map(userMapper::toDto).toList();
    }



    @PostMapping("/register")
    public UserDTO register(UserDTO dto) {
        UserEntity entity = UserEntity.builder()
                .email(dto.getEmail())
                .pseudo(dto.getPseudo())
                .password("test")
                .build();
        UserEntity user = appUserService.registerUser(entity);
        return userMapper.toDto(user);
    }

    @PostMapping("/login")
    public Integer login(String username, String password) {
        return appUserService.loginUser(username, password);
    }
}
