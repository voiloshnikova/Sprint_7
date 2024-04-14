package org.example;

import io.qameta.allure.Description;
import io.restassured.RestAssured;
import org.example.data.CommonData;
import org.example.pojo.OrdersListGetBodyResponsePojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class OrderListTest {
    CourierApi courier = new CourierApi();
    OrderApi order = new OrderApi();

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
    @Description("Пооверка что при получении списка заказов тело ответа не пустое")
    public void checkExistOrdersTest() {
        assertNotEquals(0, order.getOrderListResponse(order.getOrderJsonWithOneColor())
                .as(OrdersListGetBodyResponsePojo.class)
                .getOrders().length);
    }
}