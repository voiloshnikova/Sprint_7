package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.data.CommonData;
import org.example.data.CreateOrderPostBodyData;
import org.example.pojo.CreateOrderPostBodyResponsePojo;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class CreateOrderTest {
    OrderApi order = new OrderApi();

    @Test
    @Step("Check create order response contains track number")
    @DisplayName("Check create order response contains track number")
    @Description("Проверка что после создания заказа в теле ответа содержится числовой номер трека")
    public void checkResponseContainTrackNumberTest() {
        assertTrue(
                order.getOrderResponse(
                                order.getOrderJsonWithOneColor(
                                        CreateOrderPostBodyData.FIRSTNAME,
                                        CreateOrderPostBodyData.LASTNAME,
                                        CreateOrderPostBodyData.ADDRESS,
                                        CreateOrderPostBodyData.METRO_STATION,
                                        CreateOrderPostBodyData.PHONE,
                                        CreateOrderPostBodyData.RENT_TIME,
                                        CreateOrderPostBodyData.DELIVERY_DATE,
                                        CreateOrderPostBodyData.COMMENT,
                                        CreateOrderPostBodyData.COLOUR_BLACK
                                ),
                                CommonData.CREATE_ORDER_API)
                        .as(CreateOrderPostBodyResponsePojo.class)
                        .getTrack()
                        .matches(CommonData.REGEXP_DIGITAL));
    }

}