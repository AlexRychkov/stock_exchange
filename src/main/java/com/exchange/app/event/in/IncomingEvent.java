package com.exchange.app.event.in;

import lombok.Data;

@Data
public class IncomingEvent {
    protected String command;
    protected AbstractBusinessEvent businessEvent;
}
