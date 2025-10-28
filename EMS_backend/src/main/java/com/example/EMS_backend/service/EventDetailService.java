package com.example.EMS_backend.service;

import com.example.EMS_backend.dao.EventDetailDao;
import com.example.EMS_backend.dao.entity.EventDetailEntity;
import com.example.EMS_backend.dto.EventDetailDTO;
import com.example.EMS_backend.dto.RegistrationDTO;
import com.example.EMS_backend.dto.UserDTO;
import com.example.EMS_backend.exception.NotFoundException;
import com.example.EMS_backend.exception.SeatsNotAvailableException;
import com.example.EMS_backend.mapper.EventDetailMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventDetailService {

    @Autowired
    private EventDetailDao eventDetailDao;

    @Autowired
    private EventDetailMapper eventDetailMapper;

    @Autowired
    UserService userService;

   

    @Autowired
    RegistrationService registrationService;

    public List<EventDetailDTO> getAllEvents() {
        List<EventDetailEntity> eventEntities = eventDetailDao.findAll();
        List<EventDetailDTO> allEvents = new ArrayList<>();

        for (EventDetailEntity entity : eventEntities) {
            allEvents.add(eventDetailMapper.toDTO(entity));
        }

        return allEvents;
    }

    public EventDetailDTO getEvent(int eventId) {
        EventDetailEntity entity = eventDetailDao.findById(eventId)
                .orElseThrow(() -> new NotFoundException(eventId, "EventDetail"));
        return eventDetailMapper.toDTO(entity);
    }

    public EventDetailDTO addEvent(EventDetailDTO newEvent) {
        EventDetailEntity entity = eventDetailMapper.toEntity(newEvent);
        EventDetailEntity saved = eventDetailDao.saveAndFlush(entity);
        return eventDetailMapper.toDTO(saved);
    }

    public EventDetailDTO updateEvent(EventDetailDTO editEvent) {
        EventDetailEntity entity = eventDetailMapper.toEntity(editEvent);
        EventDetailEntity updated = eventDetailDao.save(entity);
        return eventDetailMapper.toDTO(updated);
    }

    public void deleteEvent(int eventId) {
        eventDetailDao.deleteById(eventId);
    }

    public List<EventDetailDTO> getAllUpcomingEvents() {
       List<EventDetailDTO> allEvents = getAllEvents();

    LocalDate today = LocalDate.now();

    
    List<EventDetailDTO> upcomingEvents = allEvents.stream()
            .filter(event -> {
                LocalDate eventDate = event.getEventDate();
                return eventDate.isAfter(today);
            })
            .collect(Collectors.toList());

    return upcomingEvents;

    }

    public RegistrationDTO registerForEvent(int userId,int eventId,int totalTickets)
    {
        RegistrationDTO newRegistration = new RegistrationDTO();

        System.out.println(userId);

        UserDTO registeredUser = userService.getAUser(userId);
        EventDetailDTO registeringEvent = getEvent(eventId);

        if(registeringEvent.getAvailableSeats() < totalTickets)
        {
            throw new SeatsNotAvailableException(
            "Only " + registeringEvent.getAvailableSeats() + " seats are available for this event.");
        }

        newRegistration.setRegisteredUser(registeredUser);

        int availableSeats = registeringEvent.getAvailableSeats();
        registeringEvent.setAvailableSeats(availableSeats - totalTickets);
        newRegistration.setRegisteredEventDetails(registeringEvent);

        newRegistration.setTotalTickets(totalTickets);

        double totalPrice = totalTickets * registeringEvent.getPrice();
        newRegistration.setTotalPrice(totalPrice);

        newRegistration.setStatus("Pending");

        if(registeringEvent.getEventAllRegistrations() != null)
        {
            registeringEvent.getEventAllRegistrations().add(newRegistration);
        }
        else{
            List<RegistrationDTO> eventAllRegistrations = new ArrayList<>();
            eventAllRegistrations.add(newRegistration);
            registeringEvent.setEventAllRegistrations(eventAllRegistrations);
        }

        EventDetailDTO eventDetailDTO =  updateEvent(registeringEvent);

        return eventDetailDTO.getEventAllRegistrations().getLast();

    }

    public List<String> getAllLocations()
    {
        List<EventDetailDTO> allEvents = getAllEvents();

        Set<String> allLocationsSet = new HashSet<>();
        
        allEvents.forEach((eachEvent) -> {
            allLocationsSet.add(eachEvent.getLocation());
         });

         List<String> allLocations = new ArrayList<>();

         allLocationsSet.forEach((eachLocation) -> {
            allLocations.add(eachLocation);
         });

         return allLocations;
    }
}
