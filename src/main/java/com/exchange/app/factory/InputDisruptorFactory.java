package com.exchange.app.factory;

import com.exchange.app.disruptor.input.Journaler;
import com.exchange.app.disruptor.input.Unmarshaller;
import com.exchange.app.disruptor.input.processor.BusinessProcessor;
import com.exchange.app.event.in.IncomingEvent;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import lombok.val;

import javax.inject.Named;
import javax.inject.Singleton;

import static com.lmax.disruptor.dsl.ProducerType.SINGLE;

@Factory
public class InputDisruptorFactory {
    @Value("${buffer.size}")
    private int BUFFER_SIZE;

    @Singleton
    @Context
    @Named("inputDisruptor")
    public RingBuffer<IncomingEvent> inputDisruptor(BusinessProcessor businessProcessor,
                                                    Journaler journaler,
                                                    Unmarshaller unmarshaller) {
        val disruptor = new Disruptor<IncomingEvent>(
                IncomingEvent::new,
                BUFFER_SIZE,
                DaemonThreadFactory.INSTANCE,
                SINGLE,
                new BlockingWaitStrategy()
        );
        disruptor.handleEventsWith(journaler.get(), unmarshaller.get())
                .then(businessProcessor.get());
        return disruptor.start();
    }
}
