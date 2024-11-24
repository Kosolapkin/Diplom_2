package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.constants.ApiEndpoint;
import org.example.pojo.UserCreateAndEditRequest;
import org.example.pojo.UserLoginRequest;
import org.example.pojo.UserLoginResponse;

import static io.restassured.RestAssured.given;
import static org.example.constants.ApiEndpoint.*;

public class UserSteps {

    public static RequestSpecification requestSpecification() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ApiEndpoint.BASE_URL);
    }

    @Step("Создание нового пользователя")
    public ValidatableResponse userCreate(UserCreateAndEditRequest userCreateAndEditRequest) {
        return requestSpecification()
                .body(userCreateAndEditRequest)
                .post(USER_CREATE)
                .then();
    }

    @Step("Авторизация пользователя")
    public ValidatableResponse userLogin(UserLoginRequest userLoginRequest) {
        return requestSpecification()
                .body(userLoginRequest)
                .post(USER_LOGIN)
                .then();
    }

    @Step("Изменение данных пользователя без авторизации")
    public ValidatableResponse userEdit(UserCreateAndEditRequest userCreateAndEditRequest) {
        return requestSpecification()
                .body(userCreateAndEditRequest)
                .patch(USER)
                .then();
    }


    @Step("Изменение данных пользователя после авторизации")
    public ValidatableResponse userEditAfterLogin(UserLoginRequest userLoginRequest, UserCreateAndEditRequest userCreateAndEditRequest) {
        Response response = userLogin(userLoginRequest)
                .extract().response();
        UserLoginResponse userLoginResponse = response.as(UserLoginResponse.class);
        String accessToken = userLoginResponse.getAccessToken();
        return requestSpecification()
                .header("Authorization", accessToken)
                .body(userCreateAndEditRequest)
                .patch(USER)
                .then();
    }

    @Step("Удаление пользователя без авторизации")
    public ValidatableResponse userDelete(String accessToken) {
        return requestSpecification()
                .header("Authorization", accessToken)
                .delete(USER)
                .then();
    }

    @Step("Удаление пользователя после авторизации")
    public ValidatableResponse userDeleteAfterLogin(UserLoginRequest userLoginRequest) {
        Response response = userLogin(userLoginRequest)
                .extract().response();
        UserLoginResponse userLoginResponse = response.as(UserLoginResponse.class);
        String accessToken = userLoginResponse.getAccessToken();
        return userDelete(accessToken);
    }

}
