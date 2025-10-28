package com.example.EMS_backend.service;

import com.example.EMS_backend.dao.EventDetailDao;
import com.example.EMS_backend.dao.RegistrationDao;
import com.example.EMS_backend.dao.UserDao;
import com.example.EMS_backend.dao.entity.EventDetailEntity;
import com.example.EMS_backend.dao.entity.RegistrationEntity;
import com.example.EMS_backend.dao.entity.UserEntity;
import com.example.EMS_backend.dto.RegistrationDTO;
import com.example.EMS_backend.exception.NotFoundException;
import com.example.EMS_backend.mapper.RegistrationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegistrationService {

    @Autowired
    private RegistrationDao registrationDao;

    @Autowired
    private RegistrationMapper registrationMapper;

    @Autowired
    private UserDao userDao;

    @Autowired
    private EventDetailDao eventDetailDao;

    // Get all registrations
    public List<RegistrationDTO> getAllRegistrations() {
        List<RegistrationEntity> entities = registrationDao.findAll();
        List<RegistrationDTO> dtos = new ArrayList<>();
        for (RegistrationEntity entity : entities) {
            dtos.add(registrationMapper.toDTO(entity));
        }
        return dtos;
    }

    // Get a registration by ID
    public RegistrationDTO getRegistration(int registrationId) {
        RegistrationEntity entity = registrationDao.findById(registrationId)
                .orElseThrow(() -> new NotFoundException(registrationId, "Registration"));
        return registrationMapper.toDTO(entity);
    }

    // Add a new registration
    public RegistrationDTO addRegistration(RegistrationDTO newRegistration) {
        RegistrationEntity entity = registrationMapper.toEntity(newRegistration);

        RegistrationEntity saved = registrationDao.saveAndFlush(entity);
        return registrationMapper.toDTO(saved);
    }

    // Update an existing registration
    public RegistrationDTO updateRegistration(RegistrationDTO editRegistration) {
        RegistrationEntity entity = registrationMapper.toEntity(editRegistration);
        RegistrationEntity updated = registrationDao.save(entity);
        return registrationMapper.toDTO(updated);
    }

    // Delete a registration by ID
    public void deleteRegistration(int registrationId) {
        registrationDao.deleteById(registrationId);
    }
}
