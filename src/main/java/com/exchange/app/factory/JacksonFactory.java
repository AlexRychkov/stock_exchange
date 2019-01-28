package com.exchange.app.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Primary;

import javax.inject.Singleton;

@Factory
public class JacksonFactory {
    @Singleton
    @Primary
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }
}
