package org.example;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.data.CommonData;
import org.example.pojo.CreateOrderPostBodyResponsePojo;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CreateOrderTest {
    OrderApi order = new OrderApi();

    @Before
    public void setUp() {
        RestAssured.baseURI = CommonData.SITE_ADDRESS;
    }

    @Test
    @Description("Проверка что создании заказа в теле ответа содержится числовой номер трека")
    public void checkResponseContainTrackNumberTest() {
        assertTrue(order.getOrderResponse(order.getOrderJsonWithOneColor()).as(CreateOrderPostBodyResponsePojo.class)
                .getTrack().matches(CommonData.REGEX_DIGITAL));
    }

}