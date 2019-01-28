package com.exchange.app.event.in;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransferEvent extends AbstractBusinessEvent {
    private long fromUserId;
    private long toUserId;
    private BigDecimal amount;
}