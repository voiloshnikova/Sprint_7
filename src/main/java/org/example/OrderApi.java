package org.example;

import io.restassured.response.Response;
import org.example.pojo.CreateOrderPostBodyRequestPojo;

import static io.restassured.RestAssured.given;
import static org.example.data.CommonData.requestSpec;

public class OrderApi {

    public Response getOrderResponse(CreateOrderPostBodyRequestPojo json, String createOrderApi) {
        return given()
                .spec(requestSpec)
                .body(json)
                .when()
                .post(createOrderApi);
    }

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