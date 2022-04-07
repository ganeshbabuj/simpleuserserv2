package com.example.simpleuserserv2.service;


import com.example.simpleuserserv2.resource.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User createUser(User user);
    User getUser(long id);
    void patchUser(long id, User User);
    void patchUser(long id, JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException;
    List<User> findUsers(Optional<String> username);

}
