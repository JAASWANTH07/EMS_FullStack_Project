package com.example.EMS_backend.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.EMS_backend.dao.UserDao;
import com.example.EMS_backend.dao.entity.UserEntity;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        Optional<UserEntity> userInfoEntity = userDao.findByUserName(userName);
        // but we have return UserDetails and not UserInfoEntity
        return userInfoEntity
                .map((userInfo)->new UserDetailsImpl(userInfo.getUserName(), userInfo.getPassword(), userInfo.getAllRoles()))
                .orElseThrow(()-> new UsernameNotFoundException(userName + " not found"));
    }
}
