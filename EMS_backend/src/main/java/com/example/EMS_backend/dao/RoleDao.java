package com.example.EMS_backend.dao;

import com.example.EMS_backend.dao.entity.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDao extends JpaRepository<RoleEntity,Integer>{
    Optional<RoleEntity> findByRoleName(String roleName);
}
