package ru.juli.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.juli.praktikum.util.Util;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static ru.juli.praktikum.order.OrderSteps.*;
import static ru.juli.praktikum.user.UserSteps.*;

public class CreateOrderTest extends Util{

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
}