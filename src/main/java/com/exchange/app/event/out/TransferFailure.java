package com.exchange.app.event.out;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransferFailure extends AbstractStatusFailureEvent<Long> {
    public TransferFailure(Long userId, String reason) {
        super(userId, reason);
    }
}