package com.exchange.app.event.in;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewUserEvent extends AbstractBusinessEvent {
    private long userId;
    private String name;
}