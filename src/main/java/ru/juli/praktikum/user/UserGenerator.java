package ru.juli.praktikum.user;

import com.github.javafaker.Faker;
import io.qameta.allure.Step;
import ru.juli.praktikum.client.User;

public class UserGenerator {
    static Faker faker = new Faker();

    @Step("Создание нового пользователя с рандомными данными") // Создание нового пользователя с рандомными данными
    public User createNewUnicUser() {
        String login = faker.internet().emailAddress(); // Генерация email
        String password = faker.internet().password(4, 8, true, true); // Генерация пароля
        String firstName = faker.name().username(); // Генерация имени пользователя
        // Возвращаем новый объект User
        return new User(login, password, firstName);
    }
}
