package ru.juli.praktikum.user;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.juli.praktikum.client.Credentials;
import ru.juli.praktikum.client.User;
import ru.juli.praktikum.client.StellarBurgersClientImpl;

import static ru.juli.praktikum.constants.Url.REQUEST_SPECIFICATION;
import static ru.juli.praktikum.constants.Url.RESPONSE_SPECIFICATION;

public class UserSteps {
    static StellarBurgersClientImpl client = new StellarBurgersClientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);

    @Step("Create user") // создание ользователя
    public static ValidatableResponse createUser(User user) {
        return client.createUser(user);
    }

    @Step("Login user") // логин пользователя
    public static ValidatableResponse loginUser(User user, String accessToken) {
        return client.loginUser(Credentials.fromUser(user), accessToken);
    }

    @Step("Update user") // логин пользователя
    public static ValidatableResponse updateUser(User user, String accessToken) {
        return client.updateUser(user, accessToken);
    }

    @Step("Update user") // логин пользователя без токена
    public static ValidatableResponse updateUserUnauthorized(User user) {
        return client.updateUserUnauthorized(user);
    }

}
