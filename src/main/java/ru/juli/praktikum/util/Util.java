package ru.juli.praktikum.util;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import ru.juli.praktikum.client.StellarBurgersClientImpl;
import ru.juli.praktikum.client.User;
import ru.juli.praktikum.user.UserGenerator;
import ru.juli.praktikum.user.UserSteps;

import static ru.juli.praktikum.constants.Url.REQUEST_SPECIFICATION;
import static ru.juli.praktikum.constants.Url.RESPONSE_SPECIFICATION;

public class Util {
    protected User user;
    private StellarBurgersClientImpl client = new StellarBurgersClientImpl(REQUEST_SPECIFICATION, RESPONSE_SPECIFICATION);
    protected final UserGenerator userGenerator = new UserGenerator();
    protected String accessToken;


    @Before
    @Step("Создание тестовых данных пользователя") // Создание тестовых данных пользователя
    public void setUp() {
        user = userGenerator.createNewUnicUser();
    }
    @After
    @Step("удаление данных пользователя") // удаление данных пользователя
    public void cleanUp() {
        if (accessToken != null) {
            client.deleteUser(accessToken); // удаляем созданного пользователя
        }
    }
}
