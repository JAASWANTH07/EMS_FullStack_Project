package com.example.EMS_backend.mapper;

import com.example.EMS_backend.dao.entity.RoleEntity;
import com.example.EMS_backend.dto.RoleDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RoleMapper {

    // ---------------- ENTITY → DTO ----------------
    public RoleDTO toDTO(RoleEntity entity) {
        if (entity == null) return null;

        RoleDTO dto = new RoleDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }

    // ---------------- DTO → ENTITY ----------------
    public RoleEntity toEntity(RoleDTO dto) {
        if (dto == null) return null;

        RoleEntity entity = new RoleEntity();
        BeanUtils.copyProperties(dto, entity);
        return entity;
    }
}
