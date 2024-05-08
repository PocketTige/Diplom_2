package ru.juli.praktikum.client;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static ru.juli.praktikum.constants.Url.*;

public class StellarBurgersClientImpl implements StellarBurgersClient {
        private RequestSpecification requestSpecification;
        private ResponseSpecification responseSpecification;

        public StellarBurgersClientImpl(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
            this.requestSpecification = requestSpecification;
            this.responseSpecification = responseSpecification;
        }

        @Override
        public ValidatableResponse createUser(User user) {
            return given()
                    .spec(requestSpecification)
                    .body(user)
                    .post(CREATE_USER)
                    .then()
                    .spec(responseSpecification);
        }

    @Override
    public ValidatableResponse loginUser(Credentials credentials, String accessToken) {
        return given()
                .spec(requestSpecification)
                .and()
                .header("Authorization", accessToken)
                .when()
                .body(credentials)
                .post(LOGIN_USER)
                .then()
                .spec(responseSpecification);
    }

    @Override
    public ValidatableResponse deleteUser(String accessToken) {
        return given()
                .spec(requestSpecification)
                .and()
                .header("Authorization", accessToken)
                .when()
                .delete(DELETE_USER)
                .then()
                .spec(responseSpecification);
    }
    @Override
    public ValidatableResponse updateUser(User user, String accessToken) {
        return given()
                .spec(requestSpecification)
                .and()
                .header("Authorization", accessToken)
                .when()
                .body(user)
                .patch(UPDATE_USER)
                .then()
                .spec(responseSpecification);
    }
    @Override
    public ValidatableResponse updateUserUnauthorized(User user) {
        return given()
                .spec(requestSpecification)
                .when()
                .body(user)
                .patch(UPDATE_USER)
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse createOrder(List<String> ingredients, String accessToken) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("ingredients", ingredients);
        return given()
                .spec(requestSpecification)
                .and()
                .header("Authorization", accessToken)
                .when()
                .body(requestBody)
                .post(CREATE_ORDER)
                .then()
                .spec(responseSpecification);
    }

    public ValidatableResponse createOrderUnauthorized(List<String> ingredients) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("ingredients", ingredients);
        return given()
                .spec(requestSpecification)
                .when()
                .body(requestBody)
                .post(CREATE_ORDER)
                .then()
                .spec(responseSpecification);
    }
    public ValidatableResponse createOrderNotIngredients(String accessToken) {
        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("ingredients", null);
        return given()
                .spec(requestSpecification)
                .when()
                .body(requestBody)
                .post(CREATE_ORDER)
                .then()
                .spec(responseSpecification);
    }
    @Override
    public ValidatableResponse getIngredients() {
        return given()
                .spec(requestSpecification)
                .get(GET_INGREDIENTS)
                .then()
                .spec(responseSpecification);
    }
    @Override
    public ValidatableResponse getOrdersUser(String accessToken) {
        return given()
                .spec(requestSpecification)
                .and()
                .header("Authorization", accessToken)
                .get(ORDERS_USER)
                .then()
                .spec(responseSpecification);
    }
    @Override
    public ValidatableResponse getOrdersUnauthorizedUser() {
        return given()
                .spec(requestSpecification)
                .get(ORDERS_USER)
                .then()
                .spec(responseSpecification);
    }
}