package com.example.testforclearsolutions.service;

import com.example.testforclearsolutions.rest.request.MyUserInfoUpdateRequest;
import com.example.testforclearsolutions.rest.request.UserRegistrationRequest;
import com.example.testforclearsolutions.rest.response.UserResponse;

import java.time.LocalDate;
import java.util.List;

public interface UserService {

    boolean registerNewUser(UserRegistrationRequest userRegistrationRequest);

    String update(MyUserInfoUpdateRequest newMyUserInfoUpdateRequest);

    String delete(String email);

    List<UserResponse> getUsersByBirthDateRange(LocalDate fromDate, LocalDate toDate);
}
