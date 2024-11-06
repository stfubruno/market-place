package me.bruno.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class User {

    UUID id;
    String name;
    int age;
    String email;
    String phone;
}