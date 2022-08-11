package com.example.demo.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class PriceSumDto {

    private int acceptPriceSum;

    private int notAcceptPriceSum;
}
