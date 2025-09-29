package com.example.ecommerce.validation;

import com.example.ecommerce.dto.UserUpdateDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AtLeastOneFieldValidator implements ConstraintValidator<AtLeastOneField, UserUpdateDto> {
    @Override
    public boolean isValid(UserUpdateDto dto, ConstraintValidatorContext context) {
        if (dto == null) return false;
        boolean hasUsername = dto.getUsername() != null && !dto.getUsername().isBlank();
        boolean hasEmail = dto.getEmail() != null && !dto.getEmail().isBlank();
        boolean hasRoles = dto.getRoles() != null && !dto.getRoles().isEmpty();
        return hasUsername || hasEmail || hasRoles;
    }
}
