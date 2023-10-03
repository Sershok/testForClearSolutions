package com.example.testforclearsolutions;


import com.example.testforclearsolutions.rest.UserController;
import com.example.testforclearsolutions.rest.exception.BadRequestException;
import com.example.testforclearsolutions.rest.request.MyUserInfoUpdateRequest;
import com.example.testforclearsolutions.rest.request.UserRegistrationRequest;
import com.example.testforclearsolutions.rest.response.UserResponse;
import com.example.testforclearsolutions.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.example.testforclearsolutions.rest.handler.ResponseMessagesHandler.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @InjectMocks
    private UserController userController;

    @Mock
    private UserService userService;

    @Test
    public void testRegisterUser_Success() {
        when(userService.registerNewUser(new UserRegistrationRequest())).thenReturn(true);

        var response = userController.registerUser(new UserRegistrationRequest());

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(USER_CREATED, response.getBody());

        verify(userService, times(1)).registerNewUser(new UserRegistrationRequest());
    }

    @Test
    public void testRegisterUser_Failure() {
        when(userService.registerNewUser(new UserRegistrationRequest())).thenReturn(false);
        assertThrows(BadRequestException.class, () -> userController.registerUser(new UserRegistrationRequest()));
    }

    @Test
    public void testUpdate_Success() {
        MyUserInfoUpdateRequest updateRequest = new MyUserInfoUpdateRequest();
        updateRequest.setOldEmail("oldemail@example.com");

        when(userService.update(updateRequest)).thenReturn(CHANGED_USER_INFO);

        var response = userController.updateUserInfo(updateRequest);

        assertEquals(CHANGED_USER_INFO, response);

        verify(userService, times(1)).update(updateRequest);
    }

    @Test
    public void testDelete_Success() {
        when(userService.delete(anyString())).thenReturn(USER_SUCCESSFULLY_DELETED);

        var response = userController.deleteUser(anyString());

        assertEquals(USER_SUCCESSFULLY_DELETED, response);

        verify(userService, times(1)).delete(anyString());
    }

    @Test
    public void testGetUsersByBirthDateRange_Success() {
        List<UserResponse> userList = new ArrayList<>();
        userList.add(new UserResponse());
        when(userService.getUsersByBirthDateRange(LocalDate.now(), LocalDate.now())).thenReturn(userList);

        var response = userController.getUsersByBirthDateRange(LocalDate.now(), LocalDate.now());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userList, response.getBody());

        verify(userService, times(1)).getUsersByBirthDateRange(LocalDate.now(), LocalDate.now());
    }

    @Test
    public void testGetUsersByBirthDateRange_Failure() {
        List<UserResponse> userList = new ArrayList<>();
        when(userService.getUsersByBirthDateRange(LocalDate.now(), LocalDate.now())).thenReturn(userList);

        var response = userController.getUsersByBirthDateRange(LocalDate.now(), LocalDate.now());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(userService, times(1)).getUsersByBirthDateRange(LocalDate.now(), LocalDate.now());
    }
}
