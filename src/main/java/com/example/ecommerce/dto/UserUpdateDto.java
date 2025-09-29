package com.example.ecommerce.dto;

import com.example.ecommerce.entity.UserRole;
import com.example.ecommerce.validation.AtLeastOneField;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@AtLeastOneField
public class UserUpdateDto {
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    private String username;

    @Email(message = "Email should be valid")
    private String email;

    private Set<UserRole> roles;
}
