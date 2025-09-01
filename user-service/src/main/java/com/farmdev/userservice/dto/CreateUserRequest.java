package com.farmdev.userservice.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class CreateUserRequest {
    
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50)
    private String username;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Email should be valid")
    @Size(max = 100)
    private String email;
    
    @NotBlank(message = "Password is required")
    @Size(min = 6)
    private String password;
    
    @Size(max = 50)
    private String firstName;
    
    @Size(max = 50)
    private String lastName;
} 