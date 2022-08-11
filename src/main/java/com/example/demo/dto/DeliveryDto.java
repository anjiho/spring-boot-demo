package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
public class DeliveryDto {

    private String customerName;

    private int deliveryPrice;

    private String accept;

    private String deliveryDate;

    private String bank;
}
