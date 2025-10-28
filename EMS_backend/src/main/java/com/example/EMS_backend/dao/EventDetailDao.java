package com.example.EMS_backend.dao;

import com.example.EMS_backend.dao.entity.EventDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventDetailDao extends JpaRepository<EventDetailEntity,Integer> {
}
