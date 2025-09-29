package com.example.ecommerce.dto;

import com.example.ecommerce.entity.UserRole;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {
    private Long id;
    private String username;
    private String email;
    private Set<UserRole> roles;
}
