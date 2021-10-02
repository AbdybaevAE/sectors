package com.cifer.categories.services.users;

import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class UserData {
    private final String sessionId;
    private final String firstName;
    private final List<String> sectors;

    public UserData(String sessionId, String firstName, List<String> sectors) {
        this.sessionId = sessionId;
        this.firstName = firstName;
        this.sectors = sectors;
    }
}
