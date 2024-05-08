package ru.juli.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.juli.praktikum.util.Util;

import static org.hamcrest.Matchers.equalTo;
import static ru.juli.praktikum.user.UserSteps.createUser;
import static ru.juli.praktikum.user.UserSteps.loginUser;

public class LoginUserTest extends Util {

    @Test
    @DisplayName("Login new user") // имя теста успешная авторизация пользователя
    @Description("you can Login a new user") // описание теста  логин под существующим пользователем
    public void LoginNewUserTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response2 = loginUser(user, accessToken);
        response2.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("invalid login") // имя теста авторизация пользователя с неверным логином
    @Description("login invalid Email") // описание теста  логин invalid Email
    public void LoginUserInvalidEmailTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        String currentEmail = user.getEmail(); // Получаем текущий Email
        user.setEmail(currentEmail + "nonexistent");  // Добавляем символы к логину, делая его несуществующим невалидным
        ValidatableResponse response2 = loginUser(user, accessToken);
        response2.assertThat().statusCode(401).and().assertThat().body("message", equalTo("email or password are incorrect"));
    }
    @Test
    @DisplayName("invalid Password") // имя теста авторизация пользователя с неверным паролем
    @Description("login invalid Password") // описание теста  логин invalid Password
    public void LoginUserInvalidPasswordTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        String currentPassword = user.getPassword(); // Получаем текущий Email
        user.setEmail(currentPassword + "nonexistent");  // Добавляем символы к логину, делая его несуществующим невалидным
        ValidatableResponse response2 = loginUser(user, accessToken);
        response2.assertThat().statusCode(401).and().assertThat().body("message", equalTo("email or password are incorrect"));
    }
}