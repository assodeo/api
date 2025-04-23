package org.assodeo.api.dto.user;

import org.assodeo.api.entity.User;

public record UserDto(String username, String firstName, String lastName) {

    public static UserDto from(User user) {
        return new UserDto(user.getUsername(), user.getFirstName(), user.getLastName());
    }
}
