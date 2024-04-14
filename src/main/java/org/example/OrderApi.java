package org.example;

import io.restassured.response.Response;
import org.example.data.CommonData;
import org.example.data.CreateOrderPostBodyData;
import org.example.pojo.CreateOrderPostBodyRequestPojo;

import static io.restassured.RestAssured.given;

public class OrderApi {

    public Response getOrderResponse(CreateOrderPostBodyRequestPojo json) {
        return given().header("Content-type", "application/json").body(json).when().post(CommonData.CREATE_ORDER_API);
    }

    public Response getOrderListResponse(CreateOrderPostBodyRequestPojo json) {
        return given().header("Content-type", "application/json").body(json).when().get(CommonData.ORDER_LIST_API);
    }

    public CreateOrderPostBodyRequestPojo getOrderJsonWithOneColor() {
        return new CreateOrderPostBodyRequestPojo(CreateOrderPostBodyData.FIRSTNAME, CreateOrderPostBodyData.LASTNAME,
                CreateOrderPostBodyData.ADDRESS, CreateOrderPostBodyData.METRO_STATION, CreateOrderPostBodyData.PHONE,
                CreateOrderPostBodyData.RENT_TIME, CreateOrderPostBodyData.DELIVERY_DATE, CreateOrderPostBodyData.COMMENT,
                CreateOrderPostBodyData.COLOUR_BLACK);
    }

}