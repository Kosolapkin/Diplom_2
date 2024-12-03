import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.pojo.OrderCreateRequest;
import org.example.pojo.UserCreateAndEditRequest;
import org.example.pojo.UserLoginRequest;
import org.example.steps.OrderSteps;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matchers.isA;

public class OrderCreateTest {

    public static String email = "misha.drozhzhin@yandex.ru";
    public static String password = "qwerty123";
    public static String name = "Михаил";
    public static List<String> ingredients = new ArrayList<>();

    private boolean skipDeleteUser = false;
    
    @After
    public  void deleteUser() {
        UserSteps userSteps = new UserSteps();
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

    @After
    public void ingredientsClean() {
        if (!skipDeleteUser) {
            ingredients.clear();
        }
    }

    @Test
    @DisplayName("Создание заказа после авторизации")
    @Description("Проверка возможности создания заказа после авторизации")
    public void orderCreateWithAuthorization() {

        UserCreateAndEditRequest userCreateRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(ingredients);
        UserSteps userSteps = new UserSteps();
        OrderSteps orderSteps = new OrderSteps();

        userSteps.userCreate(userCreateRequest);
        orderSteps.orderCreateAfterLogin(userLoginRequest, orderCreateRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("order.owner.email", equalTo(email))
                .and()
                .statusCode(200);;

    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    @Description("Проверка возможности создания заказа без авторизации")
    public void orderCreateWithoutAuthorization() {

        UserCreateAndEditRequest userCreateRequest = new UserCreateAndEditRequest(email, password, name);
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(ingredients);
        UserSteps userSteps = new UserSteps();
        OrderSteps orderSteps = new OrderSteps();

        userSteps.userCreate(userCreateRequest);
        orderSteps.orderCreate(orderCreateRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("order.number", isA(Integer.class))
                .and()
                .statusCode(200);;

    }

    @Test
    @DisplayName("Создание заказа после авторизации без ингредиентов")
    @Description("Проверка не возможности создания заказа после авторизации без ингредиентов")
    public void orderCreateWithAuthorizationWithoutIngredients() {

        skipDeleteUser = true;

        UserCreateAndEditRequest userCreateRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(ingredients);
        UserSteps userSteps = new UserSteps();
        OrderSteps orderSteps = new OrderSteps();

        userSteps.userCreate(userCreateRequest);
        orderSteps.orderCreateAfterLogin(userLoginRequest, orderCreateRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(400);;

    }

    @Test
    @DisplayName("Создание заказа после авторизации с неверным ингредиентом")
    @Description("Проверка не возможности создания заказа после авторизации без ингредиентов")
    public void orderCreateWithAuthorizationWithWrongIngredients() {

        UserCreateAndEditRequest userCreateRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        ingredients.add("61c0c5a71d1f82001bdaaa6d");
        ingredients.add("wrongIngredients");
        OrderCreateRequest orderCreateRequest = new OrderCreateRequest(ingredients);
        UserSteps userSteps = new UserSteps();
        OrderSteps orderSteps = new OrderSteps();

        userSteps.userCreate(userCreateRequest);
        orderSteps.orderCreateAfterLogin(userLoginRequest, orderCreateRequest)
                .assertThat().statusCode(500);

    }

}
