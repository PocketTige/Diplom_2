package ru.juli.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.juli.praktikum.client.StellarBurgersClientImpl;
import ru.juli.praktikum.client.User;
import ru.juli.praktikum.user.UserGenerator;
import ru.juli.praktikum.user.UserSteps;

import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static ru.juli.praktikum.constants.Url.REQUEST_SPECIFICATION;
import static ru.juli.praktikum.constants.Url.RESPONSE_SPECIFICATION;
import static ru.juli.praktikum.order.OrderSteps.*;
import static ru.juli.praktikum.user.UserSteps.createUser;

public class GetOrderUsersTest {
    private User user;
    private String accessToken;
    private StellarBurgersClientImpl client = new StellarBurgersClientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
    protected final UserGenerator userGenerator = new UserGenerator();
    @Before
    @Step("Создание тестовых данных пользователя") // Создание тестовых данных пользователя
    public void setUp() {
        UserSteps userSteps = new UserSteps();
        user = userGenerator.createNewUnicUser();
    }

    @Test
    @DisplayName("get orders user authorized user") // имя теста получение заказов авторизованного пользователя
    @Description("success get orders user authorized user") // описание теста  успешное получение заказов авторизованного пользователя
    public void getOrdersAuthUser() {
        ValidatableResponse response = createUser(user);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = getIngredients();
        List<String> ingredients = response_2.extract().body().jsonPath().get("data._id");         // используем список ингредиентов, который мы получили от getIngredients()
        ValidatableResponse response_3 = createOrder(ingredients, accessToken);
        ValidatableResponse response_4 = getOrdersUser(accessToken);
        response_4.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("get orders user unauthorized user") // имя теста получение заказов не авторизованного пользователя
    @Description("you cannot receive orders from an unauthorized user") // описание теста  нельзя получить заказы неавторизованного пользователя
    public void getOrdersUnauthUser() {
        ValidatableResponse response = createUser(user);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = getIngredients();
        List<String> ingredients = response_2.extract().body().jsonPath().get("data._id");
        ValidatableResponse response_3 = createOrderUnauthorized(ingredients);
        ValidatableResponse response_4 = getOrdersUnauthorizedUser();
        response_4.assertThat().statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));
    }
    @After
    @Step("удаление данных пользователя") // удаление данных пользователя
    public void cleanUp() {
        if (accessToken != null) {
            client.deleteUser(String.valueOf(accessToken)); // удаляем созданного пользователя
        }
    }
}
