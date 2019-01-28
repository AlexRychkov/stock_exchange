package com.exchange.app.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class User {
    private long userId;
    private String name;
    @JsonIgnore
    private BigDecimal balance;

    public User() {
        balance = BigDecimal.ZERO;
    }
}
