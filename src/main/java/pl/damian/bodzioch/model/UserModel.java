package pl.damian.bodzioch.model;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserModel {

    private Long id;
    private String username;
    private String password;
}
