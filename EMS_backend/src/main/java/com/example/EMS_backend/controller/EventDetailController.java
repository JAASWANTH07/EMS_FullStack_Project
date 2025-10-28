package com.example.EMS_backend.controller;

import com.example.EMS_backend.dto.EventDetailDTO;
import com.example.EMS_backend.dto.RegistrationDTO;
import com.example.EMS_backend.service.EventDetailService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@CrossOrigin
@RestController
@RequestMapping("/api/ems/events")
public class EventDetailController {

    @Autowired
    private EventDetailService eventDetailService;

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("")
    public List<EventDetailDTO> getAllEvents() {
        return eventDetailService.getAllEvents();
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("/{id}")
    public EventDetailDTO getEvent(@PathVariable("id") int eventId) {
        return eventDetailService.getEvent(eventId);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @PostMapping("/add")
    public EventDetailDTO addEvent(@RequestBody EventDetailDTO newEvent) {
        return eventDetailService.addEvent(newEvent);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @PutMapping("/update")
    public EventDetailDTO updateEvent(@RequestBody EventDetailDTO editEvent) {
        return eventDetailService.updateEvent(editEvent);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer')")
    @DeleteMapping("/delete/{id}")
    public void deleteEvent(@PathVariable("id") int eventId) {
        eventDetailService.deleteEvent(eventId);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("/upcoming")
    public List<EventDetailDTO> getAllUpcomingEvents() {
        return eventDetailService.getAllUpcomingEvents();
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("/register/{userId}/{eventId}/{totalTickets}")    
    public RegistrationDTO registerForEvent(@PathVariable("userId") int userId,@PathVariable("eventId") int eventId,@PathVariable("totalTickets") int totalTickets)
    {
        System.err.println(userId);
        return eventDetailService.registerForEvent(userId,eventId,totalTickets);
    }

    @SecurityRequirement(name = "bearerAuth")
	@PreAuthorize("hasAnyAuthority('Organizer','Participant')")
    @GetMapping("/locations")    
    public List<String> getAllLocations()
    {
        return eventDetailService.getAllLocations();
    }
    
}
