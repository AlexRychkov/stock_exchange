package com.exchange.app.event.out;

import com.exchange.app.event.in.TransferEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class TransferSuccess extends AbstractStatusEvent<TransferEvent> {
    public TransferSuccess(TransferEvent object) {
        super(object);
    }
}