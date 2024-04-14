package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.data.CommonData;
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
    public void setUp() {
        RestAssured.baseURI = CommonData.SITE_ADDRESS;
        courier.createCourier();
    }

    @After
    public void deleteCourier() {
        courier.deleteCourier();
    }


    @Test
    @DisplayName("Check that contains ID number")
    @Description("проверяем что ответ содердит цифровое значение id")
    public void checkResponseContainIntegerIdCourierTest() {
        assertTrue(courier.getAuthorizationCourierResponse(courier.getJsonWithLoginAndPassword()).as(LoginCourierPostBodyResponsePojo.class)
                .getId().matches(CommonData.REGEX_DIGITAL));
    }


    @Test
    @DisplayName("Check that exist courier can authorization")
    @Description("проверяем что курьер может авторизоваться, ответ содердит статус 200")
    public void canAuthorizationCourierTest() {
        assertEquals(SC_OK, courier.getAuthorizationCourierResponse(courier.getJsonWithLoginAndPassword()).statusCode());
    }

    @Test
    @Description("проверяем что курьер не может авторизоваться без логина")
    public void cannotAuthorizationCourierWithoutLoginTest() {
        assertEquals(SC_BAD_REQUEST, courier.getAuthorizationCourierResponse(courier.getJsonWithoutLogin()).statusCode());
    }

    @Test
    @Description("проверяем что курьер не может авторизоваться без пароля")
    public void cannotAuthorizationCourierWithoutPasswordTest() {
        assertEquals(SC_BAD_REQUEST, courier.getAuthorizationCourierResponse(courier.getJsonWithoutPassword()).statusCode());
    }

    @Test
    @Description("проверяем что курьер не может авторизоваться без логина и пароля")
    public void cannotAuthorizationCourierWithoutLoginAndPasswordTest() {
        assertEquals(SC_BAD_REQUEST, courier.getAuthorizationCourierResponse(courier.getJsonWithoutLoginAndPassword()).statusCode());
    }

    @Test
    @Description("проверяем что курьер не может авторизоваться с не верным логином")
    public void wrongLoginTest() {
        assertEquals(SC_NOT_FOUND, courier.getAuthorizationCourierResponse(courier.getJsonWithWrongLogin()).statusCode());
    }

    @Test
    @Description("проверяем что курьер не может авторизоваться с не верным паролем")
    public void wrongPasswordTest() {
        assertEquals(SC_NOT_FOUND, courier.getAuthorizationCourierResponse(courier.getJsonWithWrongPassword()).statusCode());
    }

    @Test
    @Description("проверяем что курьер не может авторизоваться с не верным логином и паролем")
    public void wrongLoginAndPasswordTest() {
        assertEquals(SC_NOT_FOUND, courier.getAuthorizationCourierResponse(courier.getJsonWithWrongLoginAndPassword()).statusCode());
    }

    @Test
    @Description("проверяем что Ошибка если курьер не создан")
    public void cannotAuthorizationWithoutCreateCourierTest() {
        assertEquals(SC_NOT_FOUND, courier.getAuthorizationCourierResponse(courier.getJsonCourierNotExist()).statusCode());
    }
}