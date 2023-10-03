package com.example.testforclearsolutions.rest.regexp;

public final class PatternHandler {
    public static final String USERNAME = "(?=\\S+$)[\\wА-Яа-яЁёҐЄІЇієїґ\\p{Punct}&&[^@]]+";
    public static final String EMAIL = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*"
            + "@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
}
