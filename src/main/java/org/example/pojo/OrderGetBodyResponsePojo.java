package org.example.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderGetBodyResponsePojo {
    private String id;
    private String courierId;
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private Integer rentTime;
    private String deliveryDate;
    private String track;
    private String[] color;
    private String comment;
    private String createdAt;
    private String updatedAt;
    private String status;

}