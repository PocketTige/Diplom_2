package ru.juli.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.juli.praktikum.client.User;
import ru.juli.praktikum.client.StellarBurgersClientImpl;
import ru.juli.praktikum.user.UserGenerator;
import ru.juli.praktikum.user.UserSteps;

import static org.hamcrest.Matchers.equalTo;
import static ru.juli.praktikum.constants.Url.REQUEST_SPECIFICATION;
import static ru.juli.praktikum.constants.Url.RESPONSE_SPECIFICATION;
import static ru.juli.praktikum.user.UserSteps.createUser;
import static ru.juli.praktikum.user.UserSteps.loginUser;

public class LoginUserTest {
    private User user;
    private StellarBurgersClientImpl client = new StellarBurgersClientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
    protected final UserGenerator userGenerator = new UserGenerator();
    private String accessToken;

    @Before
    @Step("Создание тестовых данных пользователя") // Создание тестовых данных пользователя
    public void setUp() {
        UserSteps userSteps = new UserSteps();
        user = userGenerator.createNewUnicUser();
    }

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
    @After
    @Step("удаление данных пользователя") // удаление данных пользователя
    public void cleanUp() {
        if (accessToken != null) {
            client.deleteUser(String.valueOf(accessToken)); // удаляем созданного пользователя
        }
    }
}