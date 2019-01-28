package com.exchange.app.event.out;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepositFailure extends AbstractStatusFailureEvent<Long> {
    public DepositFailure(Long userId, String reason) {
        super(userId, reason);
    }
}