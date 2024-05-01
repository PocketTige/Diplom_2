package ru.juli.praktikum.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import ru.juli.praktikum.client.StellarBurgersClientImpl;

import java.util.List;

import static ru.juli.praktikum.constants.Url.REQUEST_SPECIFICATION;
import static ru.juli.praktikum.constants.Url.RESPONSE_SPECIFICATION;

public class OrderSteps {

    static StellarBurgersClientImpl client = new StellarBurgersClientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
    @Step("Create order") // создание заказа с авторизацией
    public static ValidatableResponse createOrder(List<String> ingredients, String accessToken) {
        return client.createOrder(ingredients, accessToken);
    }
    @Step("Create order Unauthorized") // создание заказа без авторизации
    public static ValidatableResponse createOrderUnauthorized(List<String> ingredients) {
        return client.createOrderUnauthorized(ingredients);
    }

    @Step("Get ingredients") // получение всех ингридииентов
    public static ValidatableResponse getIngredients() {
        return client.getIngredients();
    }
    @Step("Create order NotIngredients") // создание заказа без ингредиентов
    public static ValidatableResponse createOrderNotIngredients(String accessToken) {
        return client.createOrderNotIngredients(accessToken);
    }
    @Step("Get orders user authorizedUser") // получение заказов авторизованного пользователя
    public static ValidatableResponse getOrdersUser(String accessToken) {
        return client.getOrdersUser(accessToken);
    }
    @Step("Get orders user UnauthorizedUser") // получение заказов неавторизованного пользователя
    public static ValidatableResponse getOrdersUnauthorizedUser() {
        return client.getOrdersUnauthorizedUser();
    }
}
