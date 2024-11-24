import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.example.pojo.UserCreateAndEditRequest;
import org.example.pojo.UserLoginRequest;
import org.example.steps.UserSteps;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;

public class UserCreateTest {

    public static String email = "misha.drozhzhin@yandex.ru";
    public static String password = "qwerty123";
    public static String name = "Михаил";

    @Test
    @DisplayName("Создание уникального пользователя")
    @Description("Проверка возможности создать нового уникального пользователя")
    public void createNewUser() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest)
                .assertThat().body("success", equalTo(true))
                .and()
                .statusCode(200);

        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    @Description("Проверка не возможности создать пользователя, который уже зарегистрирован")
    public void createDuplicateUser() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, name);
        UserLoginRequest userLoginRequest = new UserLoginRequest(email, password);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest);

        userSteps.userCreate(userCreateAndEditRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(403);

        userSteps.userDeleteAfterLogin(userLoginRequest);
    }

    @Test
    @DisplayName("Создание пользователя без поля email")
    @Description("Проверка не возможности создать пользователя без поля email")
    public void createUserWithoutEmail() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(null, password, name);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Создание пользователя без поля password")
    @Description("Проверка не возможности создать пользователя без поля password")
    public void createUserWithoutPassword() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, null, name);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

    @Test
    @DisplayName("Создание пользователя без поля name")
    @Description("Проверка не возможности создать пользователя без поля name")
    public void createUserWithoutName() {

        UserCreateAndEditRequest userCreateAndEditRequest = new UserCreateAndEditRequest(email, password, null);
        UserSteps userSteps = new UserSteps();

        userSteps.userCreate(userCreateAndEditRequest)
                .assertThat().body("success", equalTo(false))
                .and()
                .statusCode(403);
    }

}
