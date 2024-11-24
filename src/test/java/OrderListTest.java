import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.pojo.UserCreateAndEditRequest;
import org.example.pojo.UserLoginRequest;
import org.example.steps.OrderSteps;
import org.example.steps.UserSteps;
import org.junit.Test;

import java.util.List;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.instanceOf;

public class OrderListTest {

    public static String email = "misha.drozhzhin@yandex.ru";
    public static String password = "qwerty123";
    public static String name = "Михаил";

    @Test
    @DisplayName("Получение списка заказов без авторизации")
    @Description("Проверка не возможности получения списка заказов без авторизации")
    public void orderListWithoutAuthorization() {

        OrderSteps orderSteps = new OrderSteps();

        orderSteps.orderList()
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);;
    }

    @Test
    @DisplayName("Получение списка заказов после авторизации")
    @Description("Проверка не возможности получения списка заказов после авторизации")
    public void orderListWithAuthorization() {

        UserCreateAndEditRequest userCreateRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();
        OrderSteps orderSteps = new OrderSteps();

        userSteps.userCreate(userCreateRequest);
        orderSteps.orderListAfterLogin(userLoginRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("orders",instanceOf(List.class))
                .and()
                .statusCode(200);;

        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

}
