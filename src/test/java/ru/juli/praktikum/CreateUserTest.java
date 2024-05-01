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

public class CreateUserTest {
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
    @DisplayName("create new user") // имя теста успешное создание пользователя
    @Description("you can create a new user") // описание теста  пользователя можно создать
    public void CreateNewUserTest() {
        ValidatableResponse response = createUser(user);
        response.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
        accessToken = response.extract().body().jsonPath().get("accessToken");
    }
    @Test
    @DisplayName("create new user repeat") // имя теста создание пользователя с теми же самыми данными
    @Description("you cannot create two identical couriers") // описание теста нельзя создать двух одинаковых пользователя
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
    @After
    @Step("удаление данных пользователя") // удаление данных пользователя
    public void cleanUp() {
        if (accessToken != null) {
            client.deleteUser(String.valueOf(accessToken)); // удаляем созданного пользователя
        }
    }
}
