package com.example.EMS_backend.mapper;

import com.example.EMS_backend.dao.entity.*;
import com.example.EMS_backend.dto.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {

    @Autowired
    RegistrationMapper registrationMapper;

    // ---------------- ENTITY → DTO ----------------
    public UserDTO toDTO(UserEntity entity) {
        if (entity == null) return null;

        UserDTO dto = new UserDTO();
        BeanUtils.copyProperties(entity, dto);

        // Map Roles
        if (entity.getAllRoles() != null) {
            List<RoleDTO> roles = new ArrayList<>();
            for (RoleEntity role : entity.getAllRoles()) {
                RoleDTO roleDTO = new RoleDTO();
                BeanUtils.copyProperties(role, roleDTO);
                roles.add(roleDTO);
            }
            dto.setAllRoles(roles);
        }

        // Map Created Categories (shallow to avoid circular reference)
        if (entity.getCreatedCategories() != null) {
            List<CategoryDTO> categories = new ArrayList<>();
            for (CategoryEntity category : entity.getCreatedCategories()) {
                CategoryDTO categoryDTO = new CategoryDTO();
                categoryDTO.setCategoryId(category.getCategoryId());
                categoryDTO.setCategoryName(category.getCategoryName());
                if (category.getCreatedBy() != null) {
                    categoryDTO.setCreatedOrganizerId(category.getCreatedBy().getUserId());
                }
                categories.add(categoryDTO);
            }
            dto.setCreatedCategories(categories);
        }

        // Map Organized Events (shallow)
        if (entity.getOrganizedEvents() != null) {
            List<EventDetailDTO> events = new ArrayList<>();
            for (EventDetailEntity event : entity.getOrganizedEvents()) {
                EventDetailDTO eventDTO = new EventDetailDTO();
                BeanUtils.copyProperties(event, eventDTO);
                eventDTO.setOrganizerId(event.getOrganizer().getUserId());
                eventDTO.setCategoryId(event.getEventCategory().getCategoryId());
                eventDTO.setCategoryName(event.getEventCategory().getCategoryName());
                eventDTO.setOrganizer(null);
                eventDTO.setEventCategory(null); 
                eventDTO.setEventAllRegistrations(null); 
                events.add(eventDTO);
            }
            dto.setOrganizedEvents(events);
        }

      
        if (entity.getRegisteredEvents() != null) {
            List<RegistrationDTO> registrations = new ArrayList<>();
            for (RegistrationEntity registration : entity.getRegisteredEvents()) {
                RegistrationDTO registrationDTO = registrationMapper.toDTO(registration);
                
                // BeanUtils.copyProperties(registration, registrationDTO);
                // registrationDTO.setUserId(registration.getRegisteredUser().getUserId());
                // registrationDTO.setEventId(registration.getRegisteredEventDetails().getEventId());
                // registrationDTO.setRegisteredUser(null);
                // registrationDTO.setRegisteredEventDetails(null); 
                
                registrations.add(registrationDTO);
            }
            dto.setRegisteredEvents(registrations);
        }

        return dto;
    }

    // ---------------- DTO → ENTITY ----------------
    public UserEntity toEntity(UserDTO dto) {
        if (dto == null) return null;

        UserEntity entity = new UserEntity();
        BeanUtils.copyProperties(dto, entity);

        // Map Roles
        if (dto.getAllRoles() != null) {
            List<RoleEntity> roles = new ArrayList<>();
            for (RoleDTO roleDTO : dto.getAllRoles()) {
                RoleEntity roleEntity = new RoleEntity();
                BeanUtils.copyProperties(roleDTO, roleEntity);
                roles.add(roleEntity);
            }
            entity.setAllRoles(roles);
        }

        // Map Created Categories (shallow)
        if (dto.getCreatedCategories() != null) {
            List<CategoryEntity> categories = new ArrayList<>();
            for (CategoryDTO categoryDTO : dto.getCreatedCategories()) {
                CategoryEntity categoryEntity = new CategoryEntity();
                categoryEntity.setCategoryId(categoryDTO.getCategoryId());
                categoryEntity.setCategoryName(categoryDTO.getCategoryName());
                categoryEntity.setCreatedBy(entity); // back-reference
                categories.add(categoryEntity);
            }
            entity.setCreatedCategories(categories);
        }

        // Map Organized Events (shallow)
        if (dto.getOrganizedEvents() != null) {
            List<EventDetailEntity> events = new ArrayList<>();
            for (EventDetailDTO eventDTO : dto.getOrganizedEvents()) {
                EventDetailEntity eventEntity = new EventDetailEntity();
                BeanUtils.copyProperties(eventDTO, eventEntity);
                eventEntity.setOrganizer(entity); // set back-reference if needed
                eventEntity.setEventCategory(null);
                eventEntity.setEventAllRegistrations(null);
                events.add(eventEntity);
            }
            entity.setOrganizedEvents(events);
        }

        // Map Registered Events (shallow)
        if (dto.getRegisteredEvents() != null) {
            List<RegistrationEntity> registrations = new ArrayList<>();
            for (RegistrationDTO registrationDTO : dto.getRegisteredEvents()) {
                RegistrationEntity registrationEntity = new RegistrationEntity();
                BeanUtils.copyProperties(registrationDTO, registrationEntity);
                registrationEntity.setRegisteredUser(entity); // set back-reference
                registrationEntity.setRegisteredEventDetails(null);
                registrations.add(registrationEntity);
            }
            entity.setRegisteredEvents(registrations);
        }

        return entity;
    }
}
