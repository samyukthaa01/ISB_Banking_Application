package com.restAPI.banking_app.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @Size(max = 20, min = 3, message = "Invalid name. Size should be between 3 to 20.")
    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @Size(max = 20, min = 3, message = "Invalid name. Size should be between 3 to 20.")
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    @NotEmpty(message = "Address cannot be empty")

    private String address;
    @NotBlank(message = "The email cannot be blank")
    @Email(message = "Invalid email address. Please enter a proper email ID.")
    private String email;
    private String password;
    @NotNull(message = "Phone number cannot be null.")
    @Size(min = 9, max = 9, message = "Phone number must be exactly 10 digits long.")
    @Pattern(regexp = "\\d+", message = "Phone number must contain only digits and no hyphens.")
    private String phoneNumber;
    //private String status;

}
