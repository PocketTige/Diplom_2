package ru.juli.praktikum.client;

import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static ru.juli.praktikum.constants.Url.*;

public class UserClientImpl implements UserClient {
        private RequestSpecification requestSpecification;
        private ResponseSpecification responseSpecification;

        public UserClientImpl(RequestSpecification requestSpecification, ResponseSpecification responseSpecification) {
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
}