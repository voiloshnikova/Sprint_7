package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.pojo.CreateCourierPostBodyRequestPojo;
import org.example.pojo.LoginCourierPostBodyRequestPojo;
import org.example.pojo.LoginCourierPostBodyResponsePojo;

import static io.restassured.RestAssured.given;
import static org.example.data.CommonData.requestSpec;

public class CourierApi {

    @Step("Authorization Courier")
    @DisplayName("Authorization Courier")
    @Description("авторизуемся как курьер")
    public Response getAuthorizationCourierResponse(LoginCourierPostBodyRequestPojo json, String loginCourierApi) {
        return given()
                .spec(requestSpec)
                .body(json)
                .when()
                .post(loginCourierApi);
    }
    @Step("Create Courier")
    @DisplayName("Create Courier")
    @Description("создаем курьера")
    public Response getCreateCourierResponse(CreateCourierPostBodyRequestPojo json, String createCourierApi) {
        return given()
                .spec(requestSpec)
                .body(json)
                .when()
                .post(createCourierApi);
    }
    @Step("Create Courier on init")
    @DisplayName("Create Courier on init")
    @Description("создаем курьера на этапе инициализации теста")
    public void createCourier(String createCourierApi,
                              String courierLogin,
                              String courierPassword,
                              String courierName) {
        given()
                .spec(requestSpec)
                .body(getJsonWithLoginAndPasswordAndName(courierLogin, courierPassword, courierName))
                .when()
                .post(createCourierApi)
                .then()
                .statusCode(201);
    }
    @Step("Delete Courier after test")
    @DisplayName("Delete Courier after test")
    @Description("удаляем курьера после теста")
    public void deleteCourier(String loginCourierApi,
                              String createCourierApi,
                              String courierLogin,
                              String courierPassword) {
        LoginCourierPostBodyResponsePojo response = given()
                .spec(requestSpec)
                .body(getJsonWithLoginAndPassword(courierLogin, courierPassword))
                .post(loginCourierApi)
                .then()
                .extract()
                .as(LoginCourierPostBodyResponsePojo.class);
        if (response.getId() != null) {
            given()
                    .spec(requestSpec)
                    .delete(createCourierApi + "/" + response.getId().toString())
                    .then()
                    .statusCode(200);
        }
    }

    public LoginCourierPostBodyRequestPojo getJsonWithLoginAndPassword(String courierLogin, String courierPassword) {
        return new LoginCourierPostBodyRequestPojo(courierLogin, courierPassword);
    }

    public CreateCourierPostBodyRequestPojo getJsonWithLoginAndPasswordAndName(
            String courierLogin,
            String courierPassword,
            String courierName) {
        return new CreateCourierPostBodyRequestPojo(
                courierLogin,
                courierPassword,
                courierName);
    }

}