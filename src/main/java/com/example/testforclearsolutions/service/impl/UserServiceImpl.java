package com.example.testforclearsolutions.service.impl;

import com.example.testforclearsolutions.repository.UserRepository;
import com.example.testforclearsolutions.repository.model.User;
import com.example.testforclearsolutions.rest.exception.UserYoungException;
import com.example.testforclearsolutions.rest.handler.ResponseMessagesHandler;
import com.example.testforclearsolutions.rest.request.MyUserInfoUpdateRequest;
import com.example.testforclearsolutions.rest.request.UserRegistrationRequest;
import com.example.testforclearsolutions.rest.response.UserResponse;
import com.example.testforclearsolutions.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service

@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Value("${user.age}")
    private int age;
    private final UserRepository userRepository;

    @Override
    public boolean registerNewUser(UserRegistrationRequest userRegistrationRequest) {
        User userToRegister = userRegistrationDtoToUser(userRegistrationRequest);

        if (Period.between(userToRegister.getBirthDate(), LocalDate.now()).getYears() <= age) {
            throw new UserYoungException(ResponseMessagesHandler.USER_YOUNG);
        }
        return userRepository.save(userToRegister).getId() != null;
    }

    @Override
    public String update(MyUserInfoUpdateRequest newMyUserInfoUpdateRequest) {
        User newUser = userRepository.findByEmail(newMyUserInfoUpdateRequest.getOldEmail());
        newUser.setFirstName(newMyUserInfoUpdateRequest.getFirstName());
        newUser.setLastName(newMyUserInfoUpdateRequest.getLastName());
        newUser.setBirthDate(newMyUserInfoUpdateRequest.getBirthDate());
        newUser.setEmail(newMyUserInfoUpdateRequest.getEmail());
        userRepository.saveAndFlush(newUser);

        return ResponseMessagesHandler.CHANGED_USER_INFO;
    }

    @Override
    public String delete(String email) {
        userRepository.deleteByEmail(email);
        return ResponseMessagesHandler.USER_SUCCESSFULLY_DELETED;
    }

    @Override
    public List<UserResponse> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate) {
        return userToUserResponse(userRepository.findUsersByBirthDateRange(fromDate, toDate));
    }

    private User userRegistrationDtoToUser(UserRegistrationRequest userRegistrationRequest) {
        return User.builder()
                .firstName(userRegistrationRequest.getFirstName())
                .lastName(userRegistrationRequest.getLastName())
                .email(userRegistrationRequest.getEmail())
                .birthDate(userRegistrationRequest.getBirthDate())
                .build();
    }

    private List<UserResponse> userToUserResponse(List<User> users) {
        return users.stream()
                .map(user -> {
                    UserResponse userResponse = new UserResponse();
                    userResponse.setBirthDate(user.getBirthDate());
                    userResponse.setEmail(user.getEmail());
                    userResponse.setFirstName(user.getFirstName());
                    userResponse.setLastName(user.getLastName());
                    return userResponse;
                })
                .toList();
    }
}
