package ru.juli.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.juli.praktikum.util.Util;
import static org.hamcrest.Matchers.equalTo;
import static ru.juli.praktikum.user.UserSteps.createUser;

public class CreateUserTest extends Util {
    @Test
    @DisplayName("create new user") // имя теста успешное создание пользователя
    @Description("you can create a new user") // описание теста  пользователя можно создать
    public void CreateNewUserTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
        accessToken = response.extract().body().jsonPath().get("accessToken");
    }
    @Test
    @DisplayName("create new user repeat") // имя теста создание пользователя с теми же самыми данными
    @Description("you cannot create two identical couriers") // описание теста нельзя создать двух одинаковых пользователей
    public void CreateNewUserRepeatTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200); // Первое создание пользователя
        ValidatableResponse response2 = createUser(user);
        response2.assertThat().statusCode(403).and().assertThat().body("message", equalTo("User already exists")); // Попытка создать того же пользователя снова
        accessToken = response.extract().body().jsonPath().get("accessToken");
    }
    @Test
    @DisplayName("create new user required email") // имя теста создание пользователя без email
    @Description("if one of the fields is missing, the request returns an error") // описание теста если одного из полей нет (email), запрос возвращает ошибку с определенным текстом
    public void CreateNewUserRequiredEmailTest() {
        user.setEmail(null);
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(403).and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("create new user required Password") // имя теста создание пользователя без Password
    @Description("if one of the fields is missing, the request returns an error") // описание теста если одного из полей нет (Password), запрос возвращает ошибку с определенным текстом
    public void CreateNewUserRequiredNameTest() {
        user.setPassword(null);
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(403).and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
    @Test
    @DisplayName("create new user required name") // имя теста создание пользователя без name
    @Description("if one of the fields is missing, the request returns an error") // описание теста если одного из полей нет (name), запрос возвращает ошибку с определенным текстом
    public void CreateNewUserRequiredPasswordTest() {
        user.setName(null);
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(403).and().assertThat().body("message", equalTo("Email, password and name are required fields"));
    }
}
