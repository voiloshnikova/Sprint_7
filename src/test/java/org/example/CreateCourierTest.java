package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.data.CommonData;
import org.example.data.CreateCourierPostBodyData;
import org.example.pojo.CreateCourierPostBodyRequestPojo;
import org.example.pojo.CreateCourierPostBodyResponsePojo;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;


public class CreateCourierTest {
    CourierApi courier = new CourierApi();

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
    @Step("Check that courier can create")
    @DisplayName("Check that courier can create")
    @Description("проверка что курьера можно создать")
    public void canCreateCourierTest() {
        assertEquals(
                SC_CREATED,
                courier.getCreateCourierResponse(
                                courier.getJsonWithLoginAndPasswordAndName(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.COURIER_PASSWORD,
                                        CreateCourierPostBodyData.COURIER_NAME),
                                CommonData.CREATE_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check create courier message")
    @DisplayName("Check create courier message")
    @Description("проверка текста сообщения о создании курьера")
    public void checkMessageCreateCourierTest() {
        assertEquals(
                CreateCourierPostBodyData.SUCCESS_CREATE_COURIER_MESSAGE,
                courier.getCreateCourierResponse(
                                courier.getJsonWithLoginAndPasswordAndName(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.COURIER_PASSWORD,
                                        CreateCourierPostBodyData.COURIER_NAME),
                                CommonData.CREATE_COURIER_API)
                        .as(CreateCourierPostBodyResponsePojo.class)
                        .getOk());
    }

    @Test
    @Step("Check fail create two couriers with same data")
    @DisplayName("Check fail create two couriers with same data")
    @Description("проверка что нельзя создать 2х курьеров с одинаковыми данными")
    public void cannotCreateDuplicateCourierTest() {
        CreateCourierPostBodyRequestPojo json = courier.getJsonWithLoginAndPasswordAndName(
                CreateCourierPostBodyData.COURIER_LOGIN,
                CreateCourierPostBodyData.COURIER_PASSWORD,
                CreateCourierPostBodyData.COURIER_NAME);
        //создаем курьера и проверяем что статус успешного создания курьера
        assertEquals(SC_CREATED, courier.getCreateCourierResponse(json, CommonData.CREATE_COURIER_API).statusCode());
        // создаем еще одного куртьера с теми же данными и проверяем, что приходит ошибка
        assertEquals(SC_CONFLICT, courier.getCreateCourierResponse(json, CommonData.CREATE_COURIER_API).statusCode());
    }

    @Test
    @Step("Check fail create two couriers with same logins")
    @DisplayName("Check fail create two couriers with same logins")
    @Description("проверка что нельзя создать курьеров с одинаковыми логинами")
    public void cannotCreateCourierWithDuplicateNameTest() {
        //создаем курьера и проверяем что статус успешного создания курьера
        assertEquals(
                SC_CREATED,
                courier.getCreateCourierResponse(
                                courier.getJsonWithLoginAndPasswordAndName(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.COURIER_PASSWORD,
                                        CreateCourierPostBodyData.COURIER_NAME),
                                CommonData.CREATE_COURIER_API)
                        .statusCode());
        // создаем еще одного куртьера с тем же логином и проверяем, что приходит ошибка
        assertEquals(
                SC_CONFLICT,
                courier.getCreateCourierResponse(
                                courier.getJsonWithLoginAndPasswordAndName(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.COURIER_PASSWORD_NEW,
                                        CreateCourierPostBodyData.COURIER_NAME_NEW
                                ),
                                CommonData.CREATE_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail create courier without login")
    @DisplayName("Check fail create courier without login")
    @Description("проверка что нельзя создать курьера без логина")
    public void cannotCreateCourierWithoutFieldLoginTest() {
        assertEquals(
                SC_BAD_REQUEST,
                courier.getCreateCourierResponse(
                                courier.getJsonWithLoginAndPasswordAndName(
                                        CreateCourierPostBodyData.EMPTY_STRING,
                                        CreateCourierPostBodyData.COURIER_PASSWORD,
                                        CreateCourierPostBodyData.COURIER_NAME
                                ),
                                CommonData.CREATE_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail create courier without password")
    @DisplayName("Check fail create courier without password")
    @Description("проверка что нельзя создать курьера без пароля")
    public void cannotCreateCourierWithoutFieldPasswordTest() {
        assertEquals(
                SC_BAD_REQUEST,
                courier.getCreateCourierResponse(
                                courier.getJsonWithLoginAndPasswordAndName(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.EMPTY_STRING,
                                        CreateCourierPostBodyData.COURIER_NAME
                                ),
                                CommonData.CREATE_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check fail create courier without login and password")
    @DisplayName("Check fail create courier without login and password")
    @Description("проверка что нельзя создать курьера без логина и пароля")
    public void cannotCreateCourierWithoutFieldLoginAndPasswordTest() {
        assertEquals(
                SC_BAD_REQUEST,
                courier.getCreateCourierResponse(
                                courier.getJsonWithLoginAndPasswordAndName(
                                        CreateCourierPostBodyData.EMPTY_STRING,
                                        CreateCourierPostBodyData.EMPTY_STRING,
                                        CreateCourierPostBodyData.COURIER_NAME),
                                CommonData.CREATE_COURIER_API)
                        .statusCode());
    }

    @Test
    @Step("Check create courier without name")
    @DisplayName("Check create courier without name")
    @Description("проверка что можно создать курьера без имени")
    public void canCreateCourierWithoutFieldNameTest() {
        assertEquals(
                SC_CREATED,
                courier.getCreateCourierResponse(
                                courier.getJsonWithLoginAndPasswordAndName(
                                        CreateCourierPostBodyData.COURIER_LOGIN,
                                        CreateCourierPostBodyData.COURIER_PASSWORD,
                                        CreateCourierPostBodyData.EMPTY_STRING),
                                CommonData.CREATE_COURIER_API)
                        .statusCode());
    }
}