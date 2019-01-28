package com.exchange.app.disruptor.input;

import com.exchange.app.event.in.IncomingEvent;
import com.lmax.disruptor.RingBuffer;
import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import lombok.val;

import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Singleton
@Context
@Slf4j
public class FileReceiver implements Runnable {
    @Value("${input.file.path}")
    private String filePath;
    @Value("${input.file.pollinterval}")
    private int pollInterval;
    private RingBuffer<IncomingEvent> incomePublisher;

    public FileReceiver(@Named("inputDisruptor") RingBuffer<IncomingEvent> incomePublisher) {
        this.incomePublisher = incomePublisher;
    }

    @PostConstruct
    @Override
    public void run() {
        try {
            runInternal();
        } catch (InterruptedException e) {
            String errMessage = "Error occurred during polling input file: {}";
            log.error(errMessage, e);
            throw new IllegalStateException(errMessage, e);
        }
    }

    private void runInternal() throws InterruptedException {
        val inputPath = Paths.get(filePath);
        while (true) {
            if (Files.exists(inputPath)) {
                List<String> lines;
                try {
                    lines = Files.readAllLines(inputPath);
                    lines.forEach(line -> incomePublisher.publishEvent(
                            (event, sequence, arg) -> event.setCommand(line), line
                    ));
                    Files.delete(inputPath);
                } catch (IOException e) {
                    log.error("Error occur during reading input file: {}", e);
                    throw new RuntimeException(e);
                }
            }
            Thread.sleep(pollInterval);
        }
    }
}
