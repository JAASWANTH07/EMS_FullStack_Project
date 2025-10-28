package com.example.EMS_backend.mapper;

import com.example.EMS_backend.dao.entity.RegistrationEntity;
import com.example.EMS_backend.dao.entity.UserEntity;
import com.example.EMS_backend.dao.entity.EventDetailEntity;
import com.example.EMS_backend.dto.RegistrationDTO;
import com.example.EMS_backend.dto.UserDTO;
import com.example.EMS_backend.dto.EventDetailDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class RegistrationMapper {

    // ---------------- ENTITY → DTO ----------------
    public RegistrationDTO toDTO(RegistrationEntity entity) {
        if (entity == null) return null;

        RegistrationDTO dto = new RegistrationDTO();
        BeanUtils.copyProperties(entity, dto);

        // Map registeredUser (shallow)
        if (entity.getRegisteredUser() != null) {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(entity.getRegisteredUser(), userDTO);
            dto.setRegisteredUser(userDTO);
            dto.setUserId(entity.getRegisteredUser().getUserId());
        }

        // Map registeredEventDetails (shallow)
        if (entity.getRegisteredEventDetails() != null) {
            EventDetailDTO eventDTO = new EventDetailDTO();
            BeanUtils.copyProperties(entity.getRegisteredEventDetails(), eventDTO);
            eventDTO.setOrganizer(null); // avoid circular
            eventDTO.setEventCategory(null); // avoid circular
            eventDTO.setEventAllRegistrations(null); // avoid circular
            dto.setRegisteredEventDetails(eventDTO);
            dto.setEventId(entity.getRegisteredEventDetails().getEventId());
        }

        return dto;
    }

    // ---------------- DTO → ENTITY ----------------
    public RegistrationEntity toEntity(RegistrationDTO dto) {
        if (dto == null) return null;

        RegistrationEntity entity = new RegistrationEntity();
        BeanUtils.copyProperties(dto, entity);

        // Map registeredUser (shallow)
        if (dto.getRegisteredUser() != null) {
            UserEntity userEntity = new UserEntity();
            BeanUtils.copyProperties(dto.getRegisteredUser(), userEntity);
            entity.setRegisteredUser(userEntity);
        }

        // Map registeredEventDetails (shallow)
        if (dto.getRegisteredEventDetails() != null) {
            EventDetailEntity eventEntity = new EventDetailEntity();
            BeanUtils.copyProperties(dto.getRegisteredEventDetails(), eventEntity);
            eventEntity.setOrganizer(null); // avoid circular
            eventEntity.setEventCategory(null); // avoid circular
            eventEntity.setEventAllRegistrations(null); // avoid circular
            entity.setRegisteredEventDetails(eventEntity);
        }

        return entity;
    }
}
