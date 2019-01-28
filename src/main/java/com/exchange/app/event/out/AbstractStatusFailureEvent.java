package com.exchange.app.event.out;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public abstract class AbstractStatusFailureEvent<O> extends AbstractStatusEvent<O> {
    protected String reason;

    AbstractStatusFailureEvent(O object, String reason) {
        super(object);
        this.reason = reason;
    }
}
