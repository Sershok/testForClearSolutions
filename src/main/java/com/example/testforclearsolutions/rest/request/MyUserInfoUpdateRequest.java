package com.example.testforclearsolutions.rest.request;

import com.example.testforclearsolutions.rest.regexp.PatternHandler;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

import static com.example.testforclearsolutions.rest.handler.ResponseMessagesHandler.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MyUserInfoUpdateRequest {

    @Schema(example = "johndoe@gmail.com")
    @NotEmpty(message = "{" + EMPTY_EMAIL + "}")
    @Email(regexp = PatternHandler.EMAIL, message = "{" + INVALID_EMAIL + "}")
    private String oldEmail;

    @Schema(example = "John")
    @NotEmpty(message = "{" + EMPTY_USERNAME + "}")
    @Size(min = 2, max = 50, message = "{" + INVALID_USERNAME_SIZE + "}")
    @Pattern(regexp = PatternHandler.USERNAME, message = "{" + INVALID_USERNAME + "}")
    private String firstName;

    @Schema(example = "Doe")
    @NotEmpty(message = "{" + EMPTY_USERNAME + "}")
    @Size(min = 2, max = 50, message = "{" + INVALID_USERNAME_SIZE + "}")
    @Pattern(regexp = PatternHandler.USERNAME, message = "{" + INVALID_USERNAME + "}")
    private String lastName;

    @Schema(example = "johndoe@gmail.com")
    @NotEmpty(message = "{" + EMPTY_EMAIL + "}")
    @Email(regexp = PatternHandler.EMAIL, message = "{" + INVALID_EMAIL + "}")
    private String email;

    @NotEmpty(message = "{" + EMPTY_BIRTHDATE + "}")
    private LocalDate birthDate;
}
