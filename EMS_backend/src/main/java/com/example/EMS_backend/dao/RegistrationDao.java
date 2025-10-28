package com.example.EMS_backend.dao;

import com.example.EMS_backend.dao.entity.RegistrationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RegistrationDao extends JpaRepository<RegistrationEntity,Integer> {
}
