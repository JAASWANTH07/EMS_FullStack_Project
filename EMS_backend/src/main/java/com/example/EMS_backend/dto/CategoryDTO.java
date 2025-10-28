package com.example.EMS_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CategoryDTO {
    private int categoryId;

    @NotNull(message = "Organizer ID is required")
    @Positive(message = "Organizer ID must be positive")
    private int createdOrganizerId;

    @NotBlank(message = "Category name cannot be blank")
    @Size(max = 100, message = "Category name cannot exceed 100 characters")
    private String categoryName;

    private UserDTO createdBy;

    private List<EventDetailDTO> categoryEvents;

}
