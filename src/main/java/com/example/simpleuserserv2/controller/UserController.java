package com.example.simpleuserserv2.controller;

import com.example.simpleuserserv2.resource.User;
import com.example.simpleuserserv2.resource.UserCollection;
import com.example.simpleuserserv2.service.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/account")
public class UserController {

    private UserService userService;

    // Constructor Injection (vs Setter Injection)
    //@Inject
    //@Autowired (Not mandatory)
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //@RequestMapping(value="/users", method=RequestMethod.POST)
    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public User create(@RequestBody User user) {
        return userService.createUser(user);
    }


    @GetMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public User read(@PathVariable("id") Long id) {
        return userService.getUser(id);
    }


    @PutMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void updateUser(@PathVariable("id") Long id, @RequestBody User user) {
        userService.updateUser(id, user);
    }


    @PatchMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity patch(@PathVariable("id") Long id, @RequestBody JsonNode patchJson) {
        // Refer: http://jsonpatch.com/
        // https://github.com/java-json-tools/json-patch
        throw new UnsupportedOperationException();

    }

    @DeleteMapping("/users/{id}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public ResponseEntity delete(@PathVariable("id") Long id) {
        throw new UnsupportedOperationException();
    }


    @GetMapping(value = "/users")
    @ResponseStatus(code = HttpStatus.OK)
    //public UserCollection search(@RequestParam(name="username") String username) {
    public UserCollection search(@RequestParam(required = false) Optional<String> username) {

        System.out.println("Looking for: " + username);
        UserCollection userCollection = new UserCollection();
        List<User> userList = userService.findUsers(username);
        userCollection.setItems(userList);
        return userCollection;

    }

}


