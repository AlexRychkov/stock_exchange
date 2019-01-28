package com.exchange.app.factory;

import com.exchange.app.entity.User;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Value;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;

@Factory
public class DataContainersFactory {
    @Singleton
    @Named("users")
    public Map<Long, User> users(@Value("${initial.user.count}") int initialUserCount) {
        return new HashMap<>(initialUserCount);
    }
}
