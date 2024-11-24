import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.pojo.UserCreateAndEditRequest;
import org.example.pojo.UserLoginRequest;
import org.example.steps.UserSteps;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserEditTest {

    public static String email = "misha.drozhzhin@yandex.ru";
    public static String password = "qwerty123";
    public static String name = "Михаил";

    public static String newEmail = "ivan.drozhzhin@yandex.ru";
    public static String newPassword = "qwerty1234";
    public static String newName = "Иван";

    @Test
    @DisplayName("Обновление email с авторизацией")
    @Description("Проверка возможности обновления поля email с авторизацией")
    public void userEditEmailWithAuthorization() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserCreateAndEditRequest userEditRequest = new UserCreateAndEditRequest(newEmail, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserLoginRequest userNewLoginRequest = new UserLoginRequest(newEmail, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userEditAfterLogin(userLoginRequest, userEditRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("user.email", equalTo(newEmail))
                .and()
                .statusCode(200);

        userSteps.userDeleteAfterLogin(userNewLoginRequest);
    }

    @Test
    @DisplayName("Обновление email без авторизации")
    @Description("Проверка не возможности обновления поля email без авторизации")
    public void userEditEmailWithoutAuthorization() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserCreateAndEditRequest userEditRequest = new UserCreateAndEditRequest(newEmail, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userEdit(userEditRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);

        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

    @Test
    @DisplayName("Обновление password с авторизацией")
    @Description("Проверка возможности обновления поля password с авторизацией")
    public void userEditPasswordWithAuthorization() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserCreateAndEditRequest userEditRequest = new UserCreateAndEditRequest(email, newPassword, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserLoginRequest userNewLoginRequest = new UserLoginRequest(email, newPassword);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userEditAfterLogin(userLoginRequest, userEditRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);

        userSteps.userLogin(userNewLoginRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);

        userSteps.userDeleteAfterLogin(userNewLoginRequest);
    }

    @Test
    @DisplayName("Обновление password без авторизации")
    @Description("Проверка не возможности обновления поля password без авторизации")
    public void userEditPasswordWithoutAuthorization() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserCreateAndEditRequest userEditRequest = new UserCreateAndEditRequest(email, newPassword, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userEdit(userEditRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);

        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

    @Test
    @DisplayName("Обновление name с авторизацией")
    @Description("Проверка возможности обновления поля name с авторизацией")
    public void userEditNameWithAuthorization() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserCreateAndEditRequest userEditRequest = new UserCreateAndEditRequest(email, password, newName);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userEditAfterLogin(userLoginRequest, userEditRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .assertThat().body("user.name", equalTo(newName))
                .and()
                .statusCode(200);

        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

    @Test
    @DisplayName("Обновление name без авторизации")
    @Description("Проверка не возможности обновления поля name без авторизации")
    public void userEditNameWithoutAuthorization() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserCreateAndEditRequest userEditRequest = new UserCreateAndEditRequest(email, password, newName);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userEdit(userEditRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(401);

        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

}
