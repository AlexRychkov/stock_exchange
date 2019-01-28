package com.exchange.app.disruptor.input.processor;

import com.exchange.app.entity.User;
import com.exchange.app.event.in.DepositEvent;
import com.exchange.app.event.out.AbstractStatusEvent;
import com.exchange.app.event.out.DepositFailure;
import com.exchange.app.event.out.DepositSuccess;
import lombok.val;

import javax.inject.Named;
import javax.inject.Singleton;
import java.util.Map;
import java.util.function.Function;

import static java.math.BigDecimal.ZERO;

@Singleton
class DepositProcessor implements LogicProcessor<DepositEvent> {
    private final Map<Long, User> users;

    public DepositProcessor(@Named("users") Map<Long, User> users) {
        this.users = users;
    }

    @Override
    public Class<DepositEvent> getProcessingEventClass() {
        return DepositEvent.class;
    }

    @Override
    public Function<DepositEvent, AbstractStatusEvent> process() {
        return (event) -> {
            User user = users.get(event.getUserId());
            if (user == null) {
                return new DepositFailure(event.getUserId(), "user not exist");
            }
            if (event.getAmount().compareTo(ZERO) <= 0) {
                return new DepositFailure(event.getUserId(), "amount is negative");
            } else {
                val newBalance = user.getBalance().add(event.getAmount());
                user.setBalance(newBalance);
                return new DepositSuccess(user);
            }
        };
    }
}