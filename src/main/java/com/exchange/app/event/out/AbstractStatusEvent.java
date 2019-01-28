package com.exchange.app.event.out;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "result")
@JsonSubTypes({
        @JsonSubTypes.Type(value = DepositSuccess.class, name = "DepositSuccess"),
        @JsonSubTypes.Type(value = DepositFailure.class, name = "DepositFailure"),
        @JsonSubTypes.Type(value = NewUserSuccess.class, name = "NewUserSuccess"),
        @JsonSubTypes.Type(value = NewUserFailure.class, name = "NewUserFailure"),
        @JsonSubTypes.Type(value = TransferSuccess.class, name = "TransferSuccess"),
        @JsonSubTypes.Type(value = TransferFailure.class, name = "TransferFailure")
})
public abstract class AbstractStatusEvent<O> {
    protected O object;

    AbstractStatusEvent(O object) {
        this.object = object;
    }
}
