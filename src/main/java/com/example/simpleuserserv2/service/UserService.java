package com.example.simpleuserserv2.service;


import com.example.simpleuserserv2.resource.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);
    User getUser(long id);
    void updateUser(long id, User User);
    List<User> findUsers(Optional<String> username);

}
