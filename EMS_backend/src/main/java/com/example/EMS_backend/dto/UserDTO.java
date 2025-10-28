package com.example.EMS_backend.dto;

import lombok.*;

import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDTO {

    private int userId;

    @NotBlank(message = "User name must not be blank")
    @Size(min = 2, max = 100, message = "User name must be between 2 and 100 characters")
    private String userName;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email must be a valid email address")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    private String email;

    @NotBlank(message = "Password must not be blank")
    @Size(min = 8, message = "Password must be at least 8 characters long")
    @Pattern(
        regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$",
        message = "Password must contain at least one uppercase letter, one lowercase letter, one number, and one special character"
    )
    private String password;

    @Pattern(regexp = "^\\d{10}$", message = "Phone number is not valid, it should be a 10-digit number")
    private String phone;
    
    private List<RoleDTO> allRoles;

    private List<CategoryDTO> createdCategories;

    private List<EventDetailDTO> organizedEvents;

    private List<RegistrationDTO> registeredEvents;


}
