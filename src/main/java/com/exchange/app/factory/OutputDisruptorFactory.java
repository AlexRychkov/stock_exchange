package com.exchange.app.factory;

import com.exchange.app.disruptor.output.Marshaller;
import com.exchange.app.event.out.OutgoingEvent;
import com.lmax.disruptor.BlockingWaitStrategy;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;
import lombok.val;

import javax.inject.Named;
import javax.inject.Singleton;

import static com.lmax.disruptor.dsl.ProducerType.SINGLE;

@Factory
public class OutputDisruptorFactory {
    @Value("${buffer.size}")
    private int BUFFER_SIZE;

    @Singleton
    @Context
    @Named("outputDisruptor")
    public RingBuffer<OutgoingEvent> outputDisruptor(Marshaller marshaller) {
        val disruptor = new Disruptor<OutgoingEvent>(
                OutgoingEvent::new,
                BUFFER_SIZE,
                DaemonThreadFactory.INSTANCE,
                SINGLE,
                new BlockingWaitStrategy()
        );
        disruptor.handleEventsWith(marshaller.get());
        return disruptor.start();
    }
}
