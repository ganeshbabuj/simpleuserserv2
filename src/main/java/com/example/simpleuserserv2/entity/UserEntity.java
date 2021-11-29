package com.example.simpleuserserv2.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private long id;
    private String username;
    private String firstName;
    private String lastName;
    private int age;

}
