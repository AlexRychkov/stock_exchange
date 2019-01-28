package com.exchange.app.disruptor.input.processor;

import com.exchange.app.event.in.AbstractBusinessEvent;
import com.exchange.app.event.out.AbstractStatusEvent;

import java.util.function.Function;

interface LogicProcessor<E extends AbstractBusinessEvent> {
    Class<E> getProcessingEventClass();
    Function<E, AbstractStatusEvent> process();
}
