package com.exchange.app.event.out;

import com.exchange.app.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class NewUserSuccess extends AbstractStatusEvent<User> {
    public NewUserSuccess(User object) {
        super(object);
    }
}