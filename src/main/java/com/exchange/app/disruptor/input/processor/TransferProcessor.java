package com.exchange.app.disruptor.input.processor;

import com.exchange.app.entity.User;
import com.exchange.app.event.in.TransferEvent;
import com.exchange.app.event.out.AbstractStatusEvent;
import com.exchange.app.event.out.TransferFailure;
import com.exchange.app.event.out.TransferSuccess;
import com.exchange.app.mapper.UserMapper;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.function.Function;

import static com.exchange.app.mapper.UserMapper.USER_MAPPER;

@Singleton
class TransferProcessor implements LogicProcessor<TransferEvent> {
    private static final UserMapper userMapper = USER_MAPPER;
    private final Map<Long, User> users;

    public TransferProcessor(@Named("users") Map<Long, User> users) {
        this.users = users;
    }

    @Override
    public Class<TransferEvent> getProcessingEventClass() {
        return TransferEvent.class;
    }

    @Override
    public Function<TransferEvent, AbstractStatusEvent> process() {
        String userNotExistMessage = "user not exist";
        return (event) -> {
            User fromUser = users.get(event.getFromUserId());
            if (fromUser == null) {
                return new TransferFailure(event.getFromUserId(), userNotExistMessage);
            }
            if (fromUser.getBalance().compareTo(event.getAmount()) < 0) {
                return new TransferFailure(event.getFromUserId(), "not sufficient funds");
            }
            User toUser = users.get(event.getToUserId());
            if (toUser == null) {
                return new TransferFailure(event.getToUserId(), userNotExistMessage);
            }
            fromUser.setBalance(fromUser.getBalance().subtract(event.getAmount()));
            toUser.setBalance(toUser.getBalance().add(event.getAmount()));
            return new TransferSuccess(event);
        };
    }
}