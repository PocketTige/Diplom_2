package ru.juli.praktikum.client;

import io.restassured.response.ValidatableResponse;

import java.util.List;

public interface StellarBurgersClient {
    ValidatableResponse createUser(User user);
    ValidatableResponse loginUser(Credentials credentials, String accessToken);
    ValidatableResponse deleteUser(String accessToken);
    ValidatableResponse updateUser(User user, String accessToken);
    ValidatableResponse updateUserUnauthorized(User user);
    ValidatableResponse createOrder(List<String> ingredients, String accessToken);
    ValidatableResponse createOrderUnauthorized(List<String> ingredients);
    ValidatableResponse getIngredients();
    ValidatableResponse createOrderNotIngredients(String accessToken);
    ValidatableResponse getOrdersUser(String accessToken);
    ValidatableResponse getOrdersUnauthorizedUser();
}
