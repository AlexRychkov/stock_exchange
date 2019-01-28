package com.exchange.app.event.out;

import com.exchange.app.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewUserFailure extends AbstractStatusFailureEvent<User> {
    public NewUserFailure(User object, String reason) {
        super(object, reason);
    }
}