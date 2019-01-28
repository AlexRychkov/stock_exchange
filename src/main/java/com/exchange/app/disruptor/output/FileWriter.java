package com.exchange.app.disruptor.output;

import io.micronaut.context.annotation.Value;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Singleton;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardOpenOption.APPEND;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.WRITE;

@Singleton
@Slf4j
class FileWriter {
    private Path outputPath;

    public FileWriter(@Value("${output.file.path}") String filePath) {
        outputPath = Paths.get(filePath);
    }

    public void write(String json) {
        try {
            Files.write(outputPath, json.concat("\r\n").getBytes(), CREATE, WRITE, APPEND);
        } catch (Exception e) {
            log.error("Error occurred during write output. Event: {}. Exception: {}", json, e);
        }
    }
}
