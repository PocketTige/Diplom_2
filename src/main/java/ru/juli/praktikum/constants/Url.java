package ru.juli.praktikum.constants;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class Url {
    public static final String BASE_URI = "https://stellarburgers.nomoreparties.site";
    public static final String CREATE_USER = "/api/auth/register";
    public static final String DELETE_USER = "/api/auth/user";
    public static final String LOGIN_USER = "/api/auth/login";
    public static final String UPDATE_USER = "/api/auth/user";
    public static final String CREATE_ORDER = "/api/orders";
    public static final String ORDERS_USER = "/api/orders";
    public static final String GET_INGREDIENTS = "/api/ingredients";

    public static final RequestSpecification REQUEST_SPECIFICATION =
            new RequestSpecBuilder()
                    .log(LogDetail.ALL)
                    .addHeader("Content-type", "application/json")
                    .setBaseUri(BASE_URI)
                    .build();
    public static final ResponseSpecification RESPONSE_SPECIFICATION =
            new ResponseSpecBuilder().log(LogDetail.ALL).build();
}
