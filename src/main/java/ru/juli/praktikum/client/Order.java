package ru.juli.praktikum.client;

import java.util.Collections;
import java.util.List;

public class Order {
    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = Collections.singletonList(ingredients);
    }

    public Order(String ingredients) {
        this.ingredients = Collections.singletonList(ingredients);
    }

    public List<String> ingredients;

    public static Order fromIngredients(Ingredients ingredients) {
        return new Order(ingredients.toString());
    }
}
