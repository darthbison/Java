package com.junitbeyond.testcontainers.src.main.java.com.wellgrounded;

import java.math.BigDecimal;

public class HttpPricingService {
    public static BigDecimal getInitialPrice() {
        return new BigDecimal(10);
    }
}
