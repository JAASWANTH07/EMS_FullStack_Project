package com.example.EMS_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RoleDTO {
    private int roleId;

    @NotBlank(message = "Role name cannot be blank")
    @Size(max = 50, message = "Role name cannot exceed 50 characters")
    private String roleName;
}
