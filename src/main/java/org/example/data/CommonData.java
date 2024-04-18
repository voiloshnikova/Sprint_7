package org.example.data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class CommonData {
    public static final String SITE_ADDRESS = "http://qa-scooter.praktikum-services.ru/";
    public static final String CREATE_COURIER_API = "/api/v1/courier";
    public static final String LOGIN_COURIER_API = "/api/v1/courier/login";
    public static final String CREATE_ORDER_API = "/api/v1/orders";
    public static final String ORDER_LIST_API = "/api/v1/orders";
    public static final String REGEXP_DIGITAL = "-?\\d+(\\.\\d+)?";

   public static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(SITE_ADDRESS)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
    .build();
}
