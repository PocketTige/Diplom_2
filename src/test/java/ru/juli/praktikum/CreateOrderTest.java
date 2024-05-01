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
import static ru.juli.praktikum.user.UserSteps.*;

public class CreateOrderTest {
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
    @DisplayName("create new order") // имя теста успешное создание заказа с авторизацией,
    @Description("you can create a new order") // описание теста  успешное создание заказа с авторизацией,
    public void CreateNewOrderTest() {
        ValidatableResponse response = createUser(user);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = getIngredients();
        List<String> ingredients = response_2.extract().body().jsonPath().get("data._id");         // используем список ингредиентов, который мы получили от getIngredients()
        ValidatableResponse response_3 = createOrder(ingredients, accessToken);
        response_3.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("create new order Unauthorized") // имя теста  создание заказа без авторизации,
    @Description("you can create a new order Unauthorized") // описание теста  успешное создание заказа ,без авторизации,
    public void CreateNewOrderUnauthorizedTest() {
        ValidatableResponse response = createUser(user);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = getIngredients();
        List<String> ingredients = response_2.extract().body().jsonPath().get("data._id");         // используем список ингредиентов, который мы получили от getIngredients()
        ValidatableResponse response_3 = createOrderUnauthorized(ingredients);
        response_3.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("create new order NotIngredientsTest") // имя теста  создание заказа без игридиетов,
    @Description("you can create a new order NotIngredientsTest") // описание теста   создание заказа ,без игридиетов,
    public void CreateNewOrderNotIngredientsTest() {
        ValidatableResponse response = createUser(user);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = createOrderNotIngredients(accessToken);
        response_2.assertThat().statusCode(400).and().assertThat().body("message", equalTo("Ingredient ids must be provided"));
    }

    @Test
    @DisplayName("create new order") // имя теста успешное создание заказа с авторизацией,
    @Description("you can create a new order") // описание теста  успешное создание заказа с авторизацией,
    public void CreateNewOrderNonexistentIngredientsTest() {
        ValidatableResponse response = createUser(user);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = getIngredients();
        List<String> ingredients = response_2.extract().body().jsonPath().get("data._id");         // используем список ингредиентов, который мы получили от getIngredients()
        // Изменяем список ингредиентов, добавляя 'nonexistent' к каждому идентификатору
        for (int i = 0; i < ingredients.size(); i++) {
            String modifiedIngredientId = ingredients.get(i) + "nonexistent";
            ingredients.set(i, modifiedIngredientId);
        }
        ValidatableResponse response_3 = createOrder(ingredients, accessToken);
        response_3.assertThat().statusCode(500);
    }
@After
@Step("удаление данных пользователя") // удаление данных пользователя
public void cleanUp() {
    if (accessToken != null) {
        client.deleteUser(String.valueOf(accessToken)); // удаляем созданного пользователя
    }
}
}