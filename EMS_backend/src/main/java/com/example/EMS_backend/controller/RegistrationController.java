package com.example.EMS_backend.controller;

import com.example.EMS_backend.dto.RegistrationDTO;
import com.example.EMS_backend.service.RegistrationService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/api/ems/registrations")
public class RegistrationController {

    @Autowired
    private RegistrationService registrationService;

   @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @GetMapping("")
    public List<RegistrationDTO> getAllRegistrations() {
        return registrationService.getAllRegistrations();
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("/{id}")
    public RegistrationDTO getRegistration(@PathVariable("id") int registrationId) {
        return registrationService.getRegistration(registrationId);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @PostMapping("/add")
    public RegistrationDTO addRegistration(@RequestBody RegistrationDTO newRegistration) {
        return registrationService.addRegistration(newRegistration);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @PutMapping("/update")
    public RegistrationDTO updateRegistration(@RequestBody RegistrationDTO editRegistration) {
        return registrationService.updateRegistration(editRegistration);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @DeleteMapping("/delete/{id}")
    public void deleteRegistration(@PathVariable("id") int registrationId) {
        registrationService.deleteRegistration(registrationId);
    }
}
