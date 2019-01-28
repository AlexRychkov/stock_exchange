package com.exchange.app.event.in;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "eventName")
@JsonSubTypes({
        @JsonSubTypes.Type(value = NewUserEvent.class, name = "newUser"),
        @JsonSubTypes.Type(value = DepositEvent.class, name = "deposit"),
        @JsonSubTypes.Type(value = TransferEvent.class, name = "transfer")
})
public abstract class AbstractBusinessEvent {
}
