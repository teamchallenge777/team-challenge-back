package team.challenge.MobileStore.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignUpRequest(
        @NotBlank(message = "Firstname must not be blank!")
        @Pattern(regexp = "[A-Z][a-z]+",
                message = "Must start with a capital letter followed by one or more lowercase letters")
        @Size(min = 3,
                message = "Must be minimum 3 symbols long!")
        @Size(max = 15,
                message = "Must be maximum 15 symbols long!")
        String firstname,

        @NotBlank(message = "Lastname must not be blank!")
        @Pattern(regexp = "[A-Z][a-z]+",
                message = "Must start with a capital letter followed by one or more lowercase letters")
        @Size(min = 3,
                message = "Must be minimum 3 symbols long!")
        @Size(max = 15,
                message = "Must be maximum 15 symbols long!")
        String lastname,

        @NotBlank(message = "Phone number must not be blank!")
        @Size(min = 10,
                message = "Must be minimum 10 digits long!")
        @Size(max = 15,
                message = "Must be maximum 15 digits long!")
        @Pattern(regexp = "^\\+?[0-9]+",
                message = "Must contain only digits!")
        String phoneNumber,

        @NotBlank(message = "Email must not be blank!")
        @Pattern(regexp = "^[a-zA-Z][a-zA-Z0-9._%+-]{3,}$*@[A-Za-z0-9.-]+\\.[A-Z|a-z]{2,}$",
                message = "Must be a valid e-mail address!")
        String email,

        @NotBlank(message = "Password must not be blank!")
        @Pattern(regexp = ".*[!@#$%^&*()_+\\-=].*",
                message = "Must contain at least one special symbol ( !@#$%^&*()_+-= )!")
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
