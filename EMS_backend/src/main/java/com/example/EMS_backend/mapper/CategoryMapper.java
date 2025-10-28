package com.example.EMS_backend.mapper;

import com.example.EMS_backend.dao.UserDao;
import com.example.EMS_backend.dao.entity.CategoryEntity;
import com.example.EMS_backend.dao.entity.EventDetailEntity;
import com.example.EMS_backend.dao.entity.UserEntity;
import com.example.EMS_backend.dto.CategoryDTO;
import com.example.EMS_backend.dto.EventDetailDTO;
import com.example.EMS_backend.dto.UserDTO;
import com.example.EMS_backend.exception.NotFoundException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CategoryMapper {
    @Autowired
    UserDao userDao;

    // ---------------- ENTITY → DTO ----------------
    public CategoryDTO toDTO(CategoryEntity entity) {
        if (entity == null) return null;

        CategoryDTO dto = new CategoryDTO();
        BeanUtils.copyProperties(entity, dto);

        // Map createdBy (shallow)
        if (entity.getCreatedBy() != null) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(entity.getCreatedBy(), userDTO);
            dto.setCreatedBy(userDTO);
            dto.setCreatedOrganizerId(entity.getCreatedBy().getUserId());
        }

        // Map categoryEvents (shallow)
        if (entity.getCategoryEvents() != null) {
            List<EventDetailDTO> events = new ArrayList<>();
            for (EventDetailEntity event : entity.getCategoryEvents()) {
                EventDetailDTO eventDTO = new EventDetailDTO();
                BeanUtils.copyProperties(event, eventDTO);
                eventDTO.setOrganizer(null); // avoid circular
                eventDTO.setEventCategory(null); // avoid circular
                eventDTO.setEventAllRegistrations(null); // avoid circular
                events.add(eventDTO);
            }
            dto.setCategoryEvents(events);
        }

        return dto;
    }

    // ---------------- DTO → ENTITY ----------------
    public CategoryEntity toEntity(CategoryDTO dto) {
        if (dto == null) return null;

        CategoryEntity entity = new CategoryEntity();
        BeanUtils.copyProperties(dto, entity);

        UserEntity userEntity = userDao.findById(dto.getCreatedOrganizerId())
                    .orElseThrow(()-> new NotFoundException(dto.getCreatedOrganizerId(), "User"));

        entity.setCreatedBy(userEntity);

        // Map categoryEvents (shallow)
        if (dto.getCategoryEvents() != null) {
            List<EventDetailEntity> events = new ArrayList<>();
            for (EventDetailDTO eventDTO : dto.getCategoryEvents()) {
                EventDetailEntity eventEntity = new EventDetailEntity();
                BeanUtils.copyProperties(eventDTO, eventEntity);
                eventEntity.setOrganizer(null); // avoid circular
                eventEntity.setEventCategory(entity); // back-reference if needed
                eventEntity.setEventAllRegistrations(null);
                events.add(eventEntity);
            }
            entity.setCategoryEvents(events);
        }

        return entity;
    }
}
