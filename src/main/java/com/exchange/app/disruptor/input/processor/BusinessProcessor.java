package com.exchange.app.disruptor.input.processor;

import com.exchange.app.event.in.AbstractBusinessEvent;
import com.exchange.app.event.in.IncomingEvent;
import com.exchange.app.event.out.AbstractStatusEvent;
import com.exchange.app.event.out.OutgoingEvent;
import com.google.common.collect.ImmutableMap;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

@Slf4j
@Singleton
public class BusinessProcessor {
    private final Map<Class<? extends AbstractBusinessEvent>, Function<AbstractBusinessEvent, AbstractStatusEvent>> processors;
    private final RingBuffer<OutgoingEvent> outputPublisher;

    public BusinessProcessor(List<LogicProcessor> logicProcessors,
                             @Named("outputDisruptor") RingBuffer<OutgoingEvent> outputPublisher) {
        this.outputPublisher = outputPublisher;
        Map<Class<? extends AbstractBusinessEvent>, Function<AbstractBusinessEvent, AbstractStatusEvent>> tempMap =
                logicProcessors.stream().collect(toMap(LogicProcessor::getProcessingEventClass, LogicProcessor::process));
        this.processors = ImmutableMap.copyOf(tempMap);
    }

    public EventHandler<IncomingEvent> get() {
        return (inputEvent, sequence, endOfBatch) -> {
            val event = inputEvent.getBusinessEvent();
            if (event == null) {
                log.error("Error occurred with event {} in business processor", inputEvent.getCommand());
                return;
            }
            val processor = processors.get(event.getClass());
            if (processor == null) {
                log.error("Unrecognized event type in business processor: {}", event);
                return;
            }
            try {
                AbstractStatusEvent<?> outputEvent = processor.apply(event);
                outputPublisher.publishEvent((ev, seq, arg) -> {
                    ev.setBusinessEvent(event);
                    ev.setStatusEvent(outputEvent);
                }, outputEvent);
            } catch (Exception e) {
                String errMessage = "Business processing error. Event: {}. Exception: {}";
                log.error(errMessage, event, e);
                throw new IllegalStateException(errMessage, e);
            }
        };
    }
}
