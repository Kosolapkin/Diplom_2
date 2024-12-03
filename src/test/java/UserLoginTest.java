import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.pojo.UserCreateAndEditRequest;
import org.example.pojo.UserLoginRequest;
import org.example.steps.UserSteps;
import org.junit.After;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserLoginTest {

    public static String email = "misha.drozhzhin@yandex.ru";
    public static String password = "qwerty123";
    public static String name = "Михаил";

    @After
    public  void deleteUser() {
        UserSteps userSteps = new UserSteps();
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    @Description("Проверка возможности логина под существующим пользователем")
    public void userLogin() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userLogin(userLoginRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);

    }

    @Test
    @DisplayName("Логин с неверным email")
    @Description("Проверка не возможности логина с неверным email")
    public void userLoginWithWrongEmail() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userWrongLoginRequest = new UserLoginRequest("wrongEmail", password);
        UserLoginRequest userRightLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userLogin(userWrongLoginRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);

    }

    @Test
    @DisplayName("Логин с неверным password")
    @Description("Проверка не возможности логина с неверным password")
    public void userLoginWithWrongPassword() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userWrongLoginRequest = new UserLoginRequest(email, "wrongPassword");
        UserLoginRequest userRightLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userLogin(userWrongLoginRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);

    }

}
