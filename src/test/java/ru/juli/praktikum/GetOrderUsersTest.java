package ru.juli.praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import ru.juli.praktikum.util.Util;
import java.util.List;
import static org.hamcrest.Matchers.equalTo;
import static ru.juli.praktikum.order.OrderSteps.*;
import static ru.juli.praktikum.user.UserSteps.createUser;

public class GetOrderUsersTest extends Util {

    @Test
    @DisplayName("get orders user authorized user") // имя теста получение заказов авторизованного пользователя
    @Description("success get orders user authorized user") // описание теста  успешное получение заказов авторизованного пользователя
    public void getOrdersAuthUser() {
        ValidatableResponse response = createUser(user); // содаем пользователя
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = getIngredients(); // получаем список ингредиентов перед созданием заказа
        List<String> ingredients = response_2.extract().body().jsonPath().get("data._id");         // используем список ингредиентов, который мы получили от getIngredients()
        createOrder(ingredients, accessToken); // берем ингредиенты и создаем заказ
        ValidatableResponse response_3 = getOrdersUser(accessToken);
        response_3.assertThat().statusCode(200).and().assertThat().body("success", equalTo(true));
    }
    @Test
    @DisplayName("get orders user unauthorized user") // имя теста получение заказов не авторизованного пользователя
    @Description("you cannot receive orders from an unauthorized user") // описание теста  нельзя получить заказы неавторизованного пользователя
    public void getOrdersUnauthUser() {
        ValidatableResponse response = createUser(user);
        accessToken = response.extract().body().jsonPath().get("accessToken");
        ValidatableResponse response_2 = getIngredients();
        List<String> ingredients = response_2.extract().body().jsonPath().get("data._id");
        createOrderUnauthorized(ingredients);
        ValidatableResponse response_3 = getOrdersUnauthorizedUser();
        response_3.assertThat().statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));
    }
}
