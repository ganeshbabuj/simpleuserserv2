package com.example.simpleuserserv2.controller;

import com.example.simpleuserserv2.exception.ServiceException;
import com.example.simpleuserserv2.resource.User;
import com.example.simpleuserserv2.resource.UserCollection;
import com.example.simpleuserserv2.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
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
        log.info("User: {}", user);
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
        userService.patchUser(id, user);
    }


    @PatchMapping(path = "/users/{id}", consumes = "application/json-patch+json")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void patchUser(@PathVariable("id") Long id, @RequestBody JsonPatch jsonPatch) {

        // Refer: http://jsonpatch.com/
        // https://github.com/java-json-tools/json-patch
        try {
            userService.patchUser(id, jsonPatch);
        } catch (JsonPatchException | JsonProcessingException e) {
            throw new ServiceException("Unable to process", HttpStatus.BAD_REQUEST);
        }
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


