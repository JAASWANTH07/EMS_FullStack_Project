package com.example.EMS_backend.dao;

import com.example.EMS_backend.dao.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDao extends JpaRepository<CategoryEntity,Integer> {
}
