package com.example.EMS_backend.dao;

import com.example.EMS_backend.dao.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserDao extends JpaRepository<UserEntity,Integer> {
    Optional<UserEntity> findByUserName(String userName);
}
