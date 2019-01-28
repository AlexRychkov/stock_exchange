package com.exchange.app.event.in;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepositEvent extends AbstractBusinessEvent {
    private long userId;
    private BigDecimal amount;
}