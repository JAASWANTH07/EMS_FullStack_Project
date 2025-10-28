package com.example.EMS_backend.service;

import com.example.EMS_backend.dao.UserDao;
import com.example.EMS_backend.dao.entity.UserEntity;
import com.example.EMS_backend.dto.UserDTO;
import com.example.EMS_backend.exception.NotFoundException;
import com.example.EMS_backend.mapper.UserMapper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtService jwtService;

    @Autowired
    AuthenticationManager authenticationManager;

    public List<UserDTO> getAllUsers()
    {
        List<UserEntity> userEntityList = userDao.findAll();
        List<UserDTO> allUsers = new ArrayList<>();

        for(UserEntity entity: userEntityList)
        {
            allUsers.add(userMapper.toDTO(entity));
        }

        return allUsers;
    }

    public UserDTO getAUser(int userId)
    {
        
        UserEntity userEntity = userDao.findById(userId)
                .orElseThrow(() -> {
                    return new NotFoundException(userId, "User");
                });

        return userMapper.toDTO(userEntity);
    }

    public UserDTO addUser(UserDTO newUser)
    {
        newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
        UserEntity userEntity = userMapper.toEntity(newUser);

        UserEntity addedUser = userDao.saveAndFlush(userEntity);

        return userMapper.toDTO(addedUser);
    }

    public UserDTO updateUser(UserDTO editUser)
    {
        UserEntity userEntity = userMapper.toEntity(editUser);
        UserEntity updatedUser = userDao.save(userEntity);

        return userMapper.toDTO(updatedUser);
    }

    public void deleteUser(int userId)
    {
        userDao.deleteById(userId);
    }


     public Map<String, Object> login(String userName,String password) {
        
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userName, password));
        if (authentication.isAuthenticated()) {
            UserEntity userEntity = userDao.findByUserName(userName).get();
            Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", userMapper.toDTO(userEntity));
            responseData.put("token", jwtService.generateToken(userName));

            
            return responseData;
        } else {
           
            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    public Map<String,Object> register(UserDTO userDTO)
    {
        UserDTO addedUser = addUser(userDTO);

        String jwtToken = jwtService.generateToken(addedUser.getUserName());

        Map<String, Object> responseData = new HashMap<>();
            responseData.put("user", addedUser);
            responseData.put("token", jwtToken);

        return responseData;
    }


}
