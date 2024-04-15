package org.example;

import io.restassured.response.Response;
import org.example.pojo.CreateCourierPostBodyRequestPojo;
import org.example.pojo.LoginCourierPostBodyRequestPojo;
import org.example.pojo.LoginCourierPostBodyResponsePojo;

import static io.restassured.RestAssured.given;
import static org.example.data.CommonData.requestSpec;

public class CourierApi {

    public Response getAuthorizationCourierResponse(LoginCourierPostBodyRequestPojo json, String loginCourierApi) {
        return given()
                .spec(requestSpec)
                .body(json)
                .when()
                .post(loginCourierApi);
    }

    public Response getCreateCourierResponse(CreateCourierPostBodyRequestPojo json, String createCourierApi) {
        return given()
                .spec(requestSpec)
                .body(json)
                .when()
                .post(createCourierApi);
    }

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