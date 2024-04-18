package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.example.pojo.CreateOrderPostBodyRequestPojo;

import static io.restassured.RestAssured.given;
import static org.example.data.CommonData.requestSpec;

public class OrderApi {
    @Step("Create Order")
    @DisplayName("Create Order")
    @Description("создаем заказ")
    public Response getOrderResponse(CreateOrderPostBodyRequestPojo json, String createOrderApi) {
        return given()
                .spec(requestSpec)
                .body(json)
                .when()
                .post(createOrderApi);
    }
    @Step("Get Order List")
    @DisplayName("Get Order List")
    @Description("получаем список заказов")
    public Response getOrderListResponse(CreateOrderPostBodyRequestPojo json, String orderListApi) {
        return given()
                .spec(requestSpec)
                .body(json)
                .when()
                .get(orderListApi);
    }

    public CreateOrderPostBodyRequestPojo getOrderJsonWithOneColor(String firstname, String lastname, String address, String metroStation, String phone, Integer rentTime, String deliveryDate, String comment, String[] colour) {
        return new CreateOrderPostBodyRequestPojo(
                firstname,
                lastname,
                address,
                metroStation,
                phone,
                rentTime,
                deliveryDate,
                comment,
                colour
                );
    }

}