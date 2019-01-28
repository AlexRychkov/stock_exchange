package com.exchange.app.disruptor.input;

import com.exchange.app.event.in.IncomingEvent;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Singleton
@Slf4j
public class Journaler {
    public EventHandler<IncomingEvent> get() {
        return (event, sequence, endOfBatch) -> log.info("Save to journal: {}", event.getCommand());
    }
}
