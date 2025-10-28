package com.example.EMS_backend.dto;

import com.example.EMS_backend.dao.entity.CategoryEntity;
import com.example.EMS_backend.dao.entity.UserEntity;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class EventDetailDTO {

    private int eventId;

    @NotNull(message = "Organizer ID is required")
    @Positive(message = "Organizer ID must be positive")
    private Integer organizerId;

    @NotNull(message = "Category ID is required")
    @Positive(message = "Category ID must be positive")
    private Integer categoryId;

    @NotBlank(message = "Event title is required")
    @Size(max = 200, message = "Event title cannot exceed 200 characters")
    private String eventTitle;

    @NotBlank(message = "Event description is required")
    @Size(max = 2000, message = "Event description cannot exceed 2000 characters")
    private String eventDescription;

    @NotNull(message = "Event date is required")
    @FutureOrPresent(message = "Event date cannot be in the past")
    private LocalDate eventDate;

    @NotNull(message = "Start time is required")
    private LocalTime startTime;

    @NotNull(message = "End time is required")
    private LocalTime endTime;

    @NotBlank(message = "Location is required")
    private String location;

    @NotNull(message = "Capacity is required")
    @Positive(message = "Capacity must be a positive number")
    private Integer capacity;

    @NotNull(message = "Available seats is required")
    @PositiveOrZero(message = "Available seats cannot be negative")
    private Integer availableSeats;

    @NotBlank(message = "Artists field cannot be blank")
    @Size(max = 500, message = "Artists information cannot exceed 500 characters")
    private String artists;

    @Min(value = 0, message = "Age limit cannot be negative")
    private int ageLimit;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price cannot be negative")
    private Double price;

    @NotBlank(message = "Event image is required")
    private String eventImage;

    private String categoryName;

    private UserDTO organizer;

    private CategoryDTO eventCategory;

    private List<RegistrationDTO> eventAllRegistrations;

}
