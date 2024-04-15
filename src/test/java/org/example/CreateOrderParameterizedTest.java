package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.example.data.CommonData;
import org.example.data.CreateOrderPostBodyData;
import org.example.pojo.CreateOrderPostBodyRequestPojo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class CreateOrderParameterizedTest {
    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final Integer rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;
    OrderApi order = new OrderApi();

    public CreateOrderParameterizedTest(String firstName, String lastName, String address, String metroStation, String phone,
                                        Integer rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Object[][] testOrderParam() {
        return new Object[][]{
                {
                        CreateOrderPostBodyData.FIRSTNAME,
                        CreateOrderPostBodyData.LASTNAME,
                        CreateOrderPostBodyData.ADDRESS,
                        CreateOrderPostBodyData.METRO_STATION,
                        CreateOrderPostBodyData.PHONE,
                        CreateOrderPostBodyData.RENT_TIME,
                        CreateOrderPostBodyData.DELIVERY_DATE,
                        CreateOrderPostBodyData.COMMENT,
                        CreateOrderPostBodyData.COLOUR_BLACK},
                {
                        CreateOrderPostBodyData.FIRSTNAME,
                        CreateOrderPostBodyData.LASTNAME,
                        CreateOrderPostBodyData.ADDRESS,
                        CreateOrderPostBodyData.METRO_STATION,
                        CreateOrderPostBodyData.PHONE,
                        CreateOrderPostBodyData.RENT_TIME,
                        CreateOrderPostBodyData.DELIVERY_DATE,
                        CreateOrderPostBodyData.COMMENT,
                        CreateOrderPostBodyData.COLOUR_BLACK_AND_GRAY},
                {
                        CreateOrderPostBodyData.FIRSTNAME,
                        CreateOrderPostBodyData.LASTNAME,
                        CreateOrderPostBodyData.ADDRESS,
                        CreateOrderPostBodyData.METRO_STATION,
                        CreateOrderPostBodyData.PHONE,
                        CreateOrderPostBodyData.RENT_TIME,
                        CreateOrderPostBodyData.DELIVERY_DATE,
                        CreateOrderPostBodyData.COMMENT,
                        CreateOrderPostBodyData.COLOUR_EMPTY
                },
        };
    }


    @Test
    @Step("Check create order with different colors")
    @DisplayName("Check create order with different colors")
    @Description("заказ создается при разных комбинациях цвета")
    public void createOrderWithBlackColourTest() {
        CreateOrderPostBodyRequestPojo json = new CreateOrderPostBodyRequestPojo(
                firstName,
                lastName,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                color
        );
        assertEquals(
                SC_CREATED,
                order.getOrderResponse(json, CommonData.CREATE_ORDER_API).statusCode());
    }
}