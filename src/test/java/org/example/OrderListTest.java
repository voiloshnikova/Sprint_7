package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import org.example.data.CommonData;
import org.example.data.CreateCourierPostBodyData;
import org.example.data.CreateOrderPostBodyData;
import org.example.pojo.OrdersListGetBodyResponsePojo;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotEquals;

public class OrderListTest {
    CourierApi courier = new CourierApi();
    OrderApi order = new OrderApi();

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
    @Step("Check list order response not empty")
    @DisplayName("Check list order response not empty")
    @Description("Проверка что при получении списка заказов тело ответа не пустое")
    public void checkExistOrdersTest() {
        assertNotEquals(
                0,
                order.getOrderListResponse(
                                order.getOrderJsonWithOneColor(
                                        CreateOrderPostBodyData.FIRSTNAME,
                                        CreateOrderPostBodyData.LASTNAME,
                                        CreateOrderPostBodyData.ADDRESS,
                                        CreateOrderPostBodyData.METRO_STATION,
                                        CreateOrderPostBodyData.PHONE,
                                        CreateOrderPostBodyData.RENT_TIME,
                                        CreateOrderPostBodyData.DELIVERY_DATE,
                                        CreateOrderPostBodyData.COMMENT,
                                        CreateOrderPostBodyData.COLOUR_BLACK),
                                CommonData.ORDER_LIST_API)
                        .as(OrdersListGetBodyResponsePojo.class)
                        .getOrders().length);
    }
}