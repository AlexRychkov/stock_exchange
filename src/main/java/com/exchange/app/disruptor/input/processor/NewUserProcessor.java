package com.exchange.app.disruptor.input.processor;

import com.exchange.app.entity.User;
import com.exchange.app.event.in.NewUserEvent;
import com.exchange.app.event.out.AbstractStatusEvent;
import com.exchange.app.event.out.NewUserFailure;
import com.exchange.app.event.out.NewUserSuccess;
import com.exchange.app.mapper.UserMapper;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.function.Function;

import static com.exchange.app.mapper.UserMapper.USER_MAPPER;

@Singleton
class NewUserProcessor implements LogicProcessor<NewUserEvent> {
    private static final UserMapper userMapper = USER_MAPPER;
    private final Map<Long, User> users;

    public NewUserProcessor(@Named("users") Map<Long, User> users) {
        this.users = users;
    }

    @Override
    public Class<NewUserEvent> getProcessingEventClass() {
        return NewUserEvent.class;
    }

    @Override
    public Function<NewUserEvent, AbstractStatusEvent> process() {
        return (event) -> {
            User user = users.get(event.getUserId());
            if (user == null) {
                user = userMapper.map(event);
                users.put(user.getUserId(), user);
                return new NewUserSuccess(user);
            } else {
                return new NewUserFailure(user, "userId already occupied");
            }
        };
    }
}