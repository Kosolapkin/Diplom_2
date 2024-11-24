package org.example.pojo;

import java.util.List;

public class OrderCreateRequest {

    private List<String> ingredients;

    public OrderCreateRequest (List<String> ingredients) {
        this.ingredients = ingredients;
    }
     public OrderCreateRequest () {}

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
}
