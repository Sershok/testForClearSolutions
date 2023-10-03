package com.example.testforclearsolutions.rest;

import com.example.testforclearsolutions.rest.api.ApiKey;
import com.example.testforclearsolutions.rest.exception.BadRequestException;
import com.example.testforclearsolutions.rest.request.MyUserInfoUpdateRequest;
import com.example.testforclearsolutions.rest.request.UserRegistrationRequest;
import com.example.testforclearsolutions.rest.response.UserResponse;
import com.example.testforclearsolutions.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.time.LocalDate;
import java.util.List;

import static com.example.testforclearsolutions.rest.handler.ResponseMessagesHandler.USER_CREATED;
import static com.example.testforclearsolutions.rest.handler.ResponseMessagesHandler.USER_NOT_REGISTERED;

@RestController
@RequiredArgsConstructor
@Validated
@Tag(name = "User")
public class UserController {

    private final UserService userService;

    @PostMapping(value = ApiKey.USER_REGISTER, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Register new user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")
    })
    public ResponseEntity<String> registerUser(@RequestBody @Valid UserRegistrationRequest userRegistrationRequest) {

        if (userService.registerNewUser(userRegistrationRequest)) {
            return ResponseEntity.status(HttpStatus.CREATED).body(USER_CREATED);
        }

        throw new BadRequestException(USER_NOT_REGISTERED);
    }

    @PutMapping(value = ApiKey.USER_MY_INFO, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_PLAIN_VALUE})
    @Operation(summary = "Update a registered requested user's data")
    @ApiResponse(responseCode = "202", description = "ACCEPTED")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String updateUserInfo(@RequestBody @Valid MyUserInfoUpdateRequest myUserInfoUpdateRequest) {
        return userService.update(myUserInfoUpdateRequest);
    }

    @DeleteMapping(value = ApiKey.USER_DELETE)
    @Operation(summary = "delete user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public String deleteUser(@RequestBody String email) {
        return userService.delete(email);
    }

    @GetMapping(value = ApiKey.USER_BIRTHDATE_RANGE)
    @Operation(summary = "Search for users by birth date range")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "ACCEPTED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST")})
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ResponseEntity<List<UserResponse>> getUsersByBirthDateRange(@PathVariable LocalDate fromDate, @PathVariable LocalDate toDate) {
        List<UserResponse> users = userService.getUsersByBirthDateRange(fromDate, toDate);
        return users.isEmpty() ?
                new ResponseEntity<>(HttpStatus.NOT_FOUND) :
                new ResponseEntity<>(users, HttpStatus.OK);
    }
}
