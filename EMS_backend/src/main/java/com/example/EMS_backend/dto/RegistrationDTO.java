package com.example.EMS_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegistrationDTO {
    private int registrationId;

    
    @NotNull(message = "User ID is required")
    @Positive(message = "User ID must be a positive number")
    private Integer userId;

    @NotNull(message = "Event ID is required")
    @Positive(message = "Event ID must be a positive number")
    private Integer eventId;

    @NotNull(message = "Total tickets is required")
    @Min(value = 1, message = "Total tickets must be at least 1")
    private Integer totalTickets;

    private LocalDateTime registrationDate;

    @NotBlank(message = "Status cannot be blank")
    private String status;

    @NotNull(message = "Total price is required")
    @PositiveOrZero(message = "Total price must be zero or positive")
    private Double totalPrice;

    
    private UserDTO registeredUser;

    
    private EventDetailDTO registeredEventDetails;
}
