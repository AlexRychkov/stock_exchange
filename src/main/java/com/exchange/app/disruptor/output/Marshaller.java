package com.exchange.app.disruptor.output;

import com.exchange.app.event.out.OutgoingEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lmax.disruptor.EventHandler;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;

@Singleton
@Slf4j
public class Marshaller {
    private ObjectMapper objectMapper;
    private FileWriter fileWriter;

    public Marshaller(ObjectMapper objectMapper, FileWriter fileWriter) {
        this.objectMapper = objectMapper;
        this.fileWriter = fileWriter;
    }

    public EventHandler<OutgoingEvent> get() {
        return (event, sequence, endOfBatch) -> {
            String json = objectMapper.writeValueAsString(event.getStatusEvent());
            log.info("Save to file: {}", json);
            fileWriter.write(json);
        };
    }
}