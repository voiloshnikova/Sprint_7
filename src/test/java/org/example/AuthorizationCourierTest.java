package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.data.CommonData;
import org.example.data.CreateCourierPostBodyData;
import org.example.pojo.LoginCourierPostBodyResponsePojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class AuthorizationCourierTest {
    CourierApi courier = new CourierApi();

    @Before
    @Step("Preparing test data")
    @DisplayName("Preparing test data")
    public void setUp() {
        courier.createCourier(
                CommonData.CREATE_COURIER_API,
                CreateCourierPostBodyData.COURIER_LOGIN,
                CreateCourierPostBodyData.COURIER_PASSWORD,
                CreateCourierPostBodyData.COURIER_NAME);
    }

    @After
    @Step("Cleaning test data")
    @DisplayName("Cleaning test data")
    public void deleteCourier() {
        courier.deleteCourier(
                CommonData.LOGIN_COURIER_API,
                CommonData.CREATE_COURIER_API,
                CreateCourierPostBodyData.COURIER_LOGIN,
                CreateCourierPostBodyData.COURIER_PASSWORD);
    }


    @Test
    @Step("Check that contains ID number")
    @DisplayName("Check that contains ID number")
    @Description("проверяем что ответ содердит цифровое значение id")
    public void checkResponseContainIntegerIdCourierTest() {
        assertTrue(
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.COURIER_PASSWORD),
                                CommonData.LOGIN_COURIER_API)
                        .as(LoginCourierPostBodyResponsePojo.class)
                        .getId()
                        .matches(CommonData.REGEXP_DIGITAL));
    }


    @Test
    @Step("Check that exist courier can authorization")
    @DisplayName("Check that exist courier can authorization")
    @Description("проверяем что курьер может авторизоваться, ответ содердит статус 200")
    public void canAuthorizationCourierTest() {
        assertEquals(
                SC_OK,
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.COURIER_PASSWORD),
                                CommonData.LOGIN_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail authorization without login")
    @DisplayName("Check fail authorization without login")
    @Description("проверяем что курьер не может авторизоваться без логина")
    public void cannotAuthorizationCourierWithoutLoginTest() {
        assertEquals(
                SC_BAD_REQUEST,
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.EMPTY_STRING,
                                        CreateCourierPostBodyData.COURIER_PASSWORD),
                                CommonData.LOGIN_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail authorization without password")
    @DisplayName("Check fail authorization without password")
    @Description("проверяем что курьер не может авторизоваться без пароля")
    public void cannotAuthorizationCourierWithoutPasswordTest() {
        assertEquals(
                SC_BAD_REQUEST,
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.EMPTY_STRING),
                                CommonData.LOGIN_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail authorization without login and password")
    @DisplayName("Check fail authorization without login and password")
    @Description("проверяем что курьер не может авторизоваться без логина и пароля")
    public void cannotAuthorizationCourierWithoutLoginAndPasswordTest() {
        assertEquals(
                SC_BAD_REQUEST,
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.EMPTY_STRING,
                                        CreateCourierPostBodyData.EMPTY_STRING),
                                CommonData.LOGIN_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail authorization with wrong login")
    @DisplayName("Check fail authorization with wrong login")
    @Description("проверяем что курьер не может авторизоваться с не верным логином")
    public void wrongLoginTest() {
        assertEquals(
                SC_NOT_FOUND,
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.COURIER_LOGIN_NEW,
                                        CreateCourierPostBodyData.COURIER_PASSWORD),
                                CommonData.LOGIN_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail authorization with wrong password")
    @DisplayName("Check fail authorization with wrong password")
    @Description("проверяем что курьер не может авторизоваться с не верным паролем")
    public void wrongPasswordTest() {
        assertEquals(
                SC_NOT_FOUND,
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.COURIER_PASSWORD_NEW),
                                CommonData.LOGIN_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail authorization with wrong login and password")
    @DisplayName("Check fail authorization with wrong login and password")
    @Description("проверяем что курьер не может авторизоваться с не верным логином и паролем")
    public void wrongLoginAndPasswordTest() {
        assertEquals(
                SC_NOT_FOUND,
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.COURIER_LOGIN_NEW,
                                        CreateCourierPostBodyData.COURIER_PASSWORD_NEW),
                                CommonData.LOGIN_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail authorization, courier is not created")
    @DisplayName("Check fail authorization, courier is not created")
    @Description("проверяем что Ошибка если курьер не создан")
    public void cannotAuthorizationWithoutCreateCourierTest() {
        assertEquals(
                SC_NOT_FOUND,
                courier.getAuthorizationCourierResponse(
                                courier.getJsonWithLoginAndPassword(
                                        CreateCourierPostBodyData.COURIER_LOGIN_NEW,
                                        CreateCourierPostBodyData.COURIER_PASSWORD_NEW),
                                CommonData.LOGIN_COURIER_API)
                        .statusCode());
    }
}