package com.exchange.app.disruptor.input;

import com.exchange.app.event.in.AbstractBusinessEvent;
import com.exchange.app.event.in.IncomingEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Singleton
@Slf4j
public class Unmarshaller {
    private ObjectMapper objectMapper;

    public Unmarshaller(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public EventHandler<IncomingEvent> get() {
        return (event, sequence, endOfBatch) -> {
            String command = event.getCommand();
            AbstractBusinessEvent businessEvent;
            try {
                businessEvent = objectMapper.readValue(command, AbstractBusinessEvent.class);
            } catch (Exception e) {
                String errMessage = "Error occurred during un-marshalling. Command: {}. Exception: {}";
                log.error(errMessage, command, e);
                return;
//                throw new IllegalArgumentException(errMessage, e);
            }
            event.setBusinessEvent(businessEvent);
        };
    }
}