package ru.juli.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.juli.praktikum.util.Util;

import static org.hamcrest.Matchers.equalTo;
import static ru.juli.praktikum.user.UserSteps.*;

public class UpdateUserTest extends Util {
    @Test
    @DisplayName("update user Email") // имя теста успешное обновление Email пользователя
    @Description("you can not update user Email") // описание теста поля пользователя можно обновить с авторизацией
    public void UpdateUserEmailTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        String currentEmail = user.getEmail(); // Получаем текущий Email
        user.setEmail(currentEmail + "nonexistent");  // Добавляем символы
        ValidatableResponse response_2 = updateUser(user, accessToken);
        response_2.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("update user Password") // имя теста успешное обновление Password пользователя
    @Description("you can not update user Password") // описание теста поля пользователя можно обновить с авторизацией
    public void UpdateUserPasswordTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        String currentPassword = user.getPassword(); // Получаем текущий Email
        user.setPassword(currentPassword + "nonexistent");  // Добавляем символы
        ValidatableResponse response_2 = updateUser(user, accessToken);
        response_2.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("update user Name") // имя теста успешное обновление Name пользователя
    @Description("you can not update user Name") // описание теста поля пользователя можно обновить с авторизацией
    public void UpdateUserNameTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        String currentName = user.getName(); // Получаем текущий Email
        user.setName(currentName + "nonexistent");  // Добавляем символы
        ValidatableResponse response_2 = updateUser(user, accessToken);
        response_2.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("update user unauthorized") // имя теста обновление пользователя unauthorized
    @Description("you can update user") // описание теста поля пользователя нельзя обновить без авторизацией
    public void UpdateUserUnauthorizedTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = updateUserUnauthorized(user);
        response_2.assertThat().statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));

    }
}