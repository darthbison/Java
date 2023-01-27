package com.junitbeyond.testcontainers.src.test.java.com.wellgrounded;

import java.math.BigDecimal;

public class StubPrice implements Price {
    @Override
    public BigDecimal getInitialPrice() {
        return new BigDecimal("10");
    }
}
