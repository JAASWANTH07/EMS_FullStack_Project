package com.example.EMS_backend.mapper;

import com.example.EMS_backend.dao.entity.CategoryEntity;
import com.example.EMS_backend.dao.entity.EventDetailEntity;
import com.example.EMS_backend.dao.entity.RegistrationEntity;
import com.example.EMS_backend.dao.entity.UserEntity;
import com.example.EMS_backend.dto.EventDetailDTO;
import com.example.EMS_backend.dto.RegistrationDTO;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EventDetailMapper {

    @Autowired
    UserMapper userMapper;

    @Autowired
    RegistrationMapper registrationMapper;

    @Autowired
    CategoryMapper categoryMapper;

    public EventDetailDTO toDTO(EventDetailEntity entity) {
        if (entity == null) return null;

        EventDetailDTO dto = new EventDetailDTO();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getOrganizer() != null) {
            dto.setOrganizer(userMapper.toDTO(entity.getOrganizer()));
            dto.setOrganizerId(entity.getOrganizer().getUserId());
        }

        if (entity.getEventCategory() != null) {
            dto.setEventCategory(categoryMapper.toDTO(entity.getEventCategory()));
            dto.setCategoryId(entity.getEventCategory().getCategoryId());
            dto.setCategoryName(entity.getEventCategory().getCategoryName());
        }

        if (entity.getEventAllRegistrations() != null) {
            List<RegistrationDTO> registrations = new ArrayList<>();
            for (var regEntity : entity.getEventAllRegistrations()) {
                registrations.add(registrationMapper.toDTO(regEntity));
            }
            dto.setEventAllRegistrations(registrations);
        }

        return dto;
    }

    public EventDetailEntity toEntity(EventDetailDTO dto) {
        if (dto == null) return null;

        EventDetailEntity entity = new EventDetailEntity();
        BeanUtils.copyProperties(dto, entity);

         if (dto.getOrganizerId() != null) {
        UserEntity user = new UserEntity();
        user.setUserId(dto.getOrganizerId());
        entity.setOrganizer(user);
    } else if (dto.getOrganizer() != null) {
        entity.setOrganizer(userMapper.toEntity(dto.getOrganizer()));
    }

    // If only categoryId is given, set the reference manually
    if (dto.getCategoryId() != null) {
        CategoryEntity category = new CategoryEntity();
        category.setCategoryId(dto.getCategoryId());
        entity.setEventCategory(category);
    } else if (dto.getEventCategory() != null) {
        entity.setEventCategory(categoryMapper.toEntity(dto.getEventCategory()));
    }

        if (dto.getEventAllRegistrations() != null) {
            List<RegistrationEntity> registrations = new ArrayList<>();
            for (var regDTO : dto.getEventAllRegistrations()) {
                registrations.add(registrationMapper.toEntity(regDTO));
            }
            entity.setEventAllRegistrations(registrations);
        }

        return entity;
    }
}
