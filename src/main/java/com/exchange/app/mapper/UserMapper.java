package com.exchange.app.mapper;

import com.exchange.app.entity.User;
import com.exchange.app.event.in.NewUserEvent;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper USER_MAPPER = Mappers.getMapper(UserMapper.class);

    User map(NewUserEvent newUser);
}