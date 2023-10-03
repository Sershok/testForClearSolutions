package com.example.testforclearsolutions.rest.api;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiKey {
    public static final String API = "/api/v1";
    public static final String USER = API + "/user";
    public static final String USER_REGISTER = USER + "/register";
    public static final String USER_MY_INFO = USER + "/my-info";
    public static final String USER_DELETE = USER + "/delete";
    public static final String USER_BIRTHDATE_RANGE = USER + "/birthdate-range/{fromDate}/{toDate}";
}
