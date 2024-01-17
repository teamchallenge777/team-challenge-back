package team.challenge.MobileStore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record LoginRequest(
        @NotBlank(message = "Email must not be blank!")
        @NotEmpty(message = "Email must not be empty!")
        @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
                message = "Must be a valid e-mail address!")
        @Size(min = 11)
        String email,
        @NotBlank(message = "Password must not be blank!")
        @NotEmpty(message = "Password must not be empty!")
        @Pattern(regexp = "[A-Za-z\\d]{6,}",
                message = "Must be minimum 6 symbols long, using digits and latin letters!")
        @Pattern(regexp = ".*\\d.*",
                message = "Must contain at least one digit!")
        @Pattern(regexp = ".*[A-Z].*",
                message = "Must contain at least one uppercase letter!")
        @Pattern(regexp = ".*[a-z].*",
                message = "Must contain at least one lowercase letter!")
        @Size(min = 6, message = "Must be minimum 6 symbols long!")
        @Size(max = 16, message = "Must be maximum 16 symbols long!")
        String password
) {
}
