package me.bruno.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class User {

    String name;
    int id;
    String email;
    String password;

}