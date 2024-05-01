package ru.juli.praktikum.client;

import io.restassured.response.ValidatableResponse;

public interface UserClient {
    ValidatableResponse createUser(User user);
    ValidatableResponse loginUser(Credentials credentials, String accessToken);
    ValidatableResponse deleteUser(String accessToken);
    ValidatableResponse updateUser(User user, String accessToken);
    ValidatableResponse updateUserUnauthorized(User user);


//    ValidatableResponse createOrder(Order order);
//    ValidatableResponse cancelOrder(String trackCredentials);
//    ValidatableResponse getOrdersUser();
}
