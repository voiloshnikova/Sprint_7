package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.data.CommonData;
import org.example.data.CreateCourierPostBodyData;
import org.example.pojo.CreateCourierPostBodyRequestPojo;
import org.example.pojo.CreateCourierPostBodyResponsePojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;


public class CreateCourierTest {
    CourierApi courier = new CourierApi();

    @Before
    public void setUp() {
        RestAssured.baseURI = CommonData.SITE_ADDRESS;
    }

    @After
    public void deleteCourier() {
        courier.deleteCourier();
    }

    @Test
    @DisplayName("Check that courier can create")
    @Description("проверка что курьера можно создать")
    public void canCreateCourierTest() {
        assertEquals(SC_CREATED, courier.getCreateCourierResponse(courier.getJsonWithLoginAndPasswordAndName()).statusCode());
    }

    @Test
    @Description("проверка текста сообщения о создании курьера")
    public void checkMessageCreateCourierTest() {
        assertEquals(CreateCourierPostBodyData.SUCCESS_CREATE_COURIER_MASSAGE,
                courier.getCreateCourierResponse(courier.getJsonWithLoginAndPasswordAndName()).as(CreateCourierPostBodyResponsePojo.class).getOk());
    }

    @Test
    @Description("проверка что нельзя создать 2х курьеров с одинаковыми данными")
    public void cannotCreateDiplicateCourierTest() {
        CreateCourierPostBodyRequestPojo json = courier.getJsonWithLoginAndPasswordAndName();
        //создаем курьера и проверяем что статус успешного создания курьера
        assertEquals(SC_CREATED, courier.getCreateCourierResponse(json).statusCode());
        // создаем еще одного куртьера с теми же данными и проверяем, что приходит ошибка
        assertEquals(SC_CONFLICT, courier.getCreateCourierResponse(json).statusCode());
    }

    @Test
    @Description("проверка что нельзя создать курьеров с одинаковыми логинами")
    public void cannotCreateCourierWithDuplicateNameTest() {
        //создаем курьера и проверяем что статус успешного создания курьера
        assertEquals(SC_CREATED, courier.getCreateCourierResponse(courier.getJsonWithLoginAndPasswordAndName()).statusCode());
        // создаем еще одного куртьера с тем же логином и проверяем, что приходит ошибка
        assertEquals(SC_CONFLICT, courier.getCreateCourierResponse(courier.getJsonWithLoginAndPasswordNewAndNameNew()).statusCode());
    }

    @Test
    @Description("проверка что нельзя создать курьера без логина")
    public void cannotCreateCourierWithoutFieldLoginTest() {
        assertEquals(SC_BAD_REQUEST, courier.getCreateCourierResponse(courier.getJsonWithoutLoginWithName()).statusCode());
    }

    @Test
    @Description("проверка что нельзя создать курьера без пароля")
    public void cannotCreateCourierWithoutFieldPasswordTest() {
        assertEquals(SC_BAD_REQUEST, courier.getCreateCourierResponse(courier.getJsonWithoutPasswordWithName()).statusCode());
    }

    @Test
    @Description("проверка что нельзя создать курьера без логина и пароля")
    public void cannotCreateCourierWithoutFieldLoginAndPasswordTest() {
        assertEquals(SC_BAD_REQUEST, courier.getCreateCourierResponse(courier.getJsonWithoutLoginAndPasswordWithName()).statusCode());
    }

    @Test
    @Description("проверка что можно создать курьера без имени")
    public void canCreateCourierWithoutFieldNameTest() {
        assertEquals(SC_CREATED, courier.getCreateCourierResponse(courier.getJsonWithLoginAndPasswordWithoutName()).statusCode());
    }
}