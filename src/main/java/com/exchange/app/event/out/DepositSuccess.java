package com.exchange.app.event.out;

import com.exchange.app.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class DepositSuccess extends AbstractStatusEvent<User> {
    public DepositSuccess(User object) {
        super(object);
    }
}