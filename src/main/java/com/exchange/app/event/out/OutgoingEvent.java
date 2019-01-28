package com.exchange.app.event.out;

import com.exchange.app.event.in.AbstractBusinessEvent;
import lombok.Data;

@Data
public class OutgoingEvent {
    protected AbstractBusinessEvent businessEvent;
    protected AbstractStatusEvent<?> statusEvent;
}
