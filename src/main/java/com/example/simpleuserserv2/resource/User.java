package com.example.simpleuserserv2.resource;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    private long id;
    private String username;

    private String firstName;
    private String lastName;
    private int age;

}
