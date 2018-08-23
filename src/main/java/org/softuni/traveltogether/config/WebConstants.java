package org.softuni.traveltogether.config;

public final class WebConstants {
    private WebConstants() {
    }

    public static final String ERROR_URL = "/error";
    public static final String ACCESS_DENIED_URL = "/unauthorized";
    public static final String LOGIN_URL = "/login";
    public static final String LOGOUT_URL = "/logout";
    public static final String HOME_URL = "/home";
    public static final String[] ANONYMOUS_URLS = {"/", "/login", "/register"};
    public static final String[] PERMITTED_URLS = {"/css/**", "/scripts/**", "/assets/**", "/favicon.ico", "/travel_api/search/findTop5ByOrderByPublishedAtDesc", ERROR_URL};

    public static final String USER_PROFILE_VIEW_MODEL_NAME = "user";

    public static final String UNIQUE_USERNAME_CONSTRAINT_NAME = "uniqueUsername";
    public static final String UNIQUE_EMAIL_CONSTRAINT_NAME = "uniqueEmail";
}
