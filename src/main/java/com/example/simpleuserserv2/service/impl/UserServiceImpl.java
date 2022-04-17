package com.example.simpleuserserv2.service.impl;

import com.example.simpleuserserv2.entity.UserEntity;
import com.example.simpleuserserv2.exception.NotFoundException;
import com.example.simpleuserserv2.resource.User;
import com.example.simpleuserserv2.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
@Scope("singleton") // Not mandatory
public class UserServiceImpl implements UserService {

    AtomicLong userCounter = new AtomicLong(101L);

    @Autowired
    private Jackson2ObjectMapperBuilder mapperBuilder;

    // Setter injection just for example
    @Autowired
    private ModelMapper modelMapper;

    //@Value("user_")
    //@Value("${username.prefix}")
    @Value("${username.prefix: user_}")
    private String usernamePrefix;

    // Map to hold UserEntity just for demo purposes
    // Component is singleton. Only one map will exist
    private Map<Long, UserEntity> userEntityMap = new ConcurrentHashMap<Long, UserEntity>() {{
        put(101L, new UserEntity(101L, "usr_101", "First101", "Last101", 25));

    }};


    @Override
    public User createUser(User user) {

        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        Long id = userCounter.incrementAndGet();
        userEntity.setId(id);
        userEntity.setUsername(generateUsername(id));
        userEntityMap.put(id, userEntity);
        User savedUser = modelMapper.map(userEntity, User.class);
        return savedUser;
    }

    @Override
    public User getUser(long id) {

        UserEntity userEntity = userEntityMap.get(id);
        if (Objects.isNull(userEntity)) {
            throw new NotFoundException("User Not Found");
        }
        return modelMapper.map(userEntity, User.class);
    }

    @Override
    public List<User> findUsers(Optional<String> username) {

        List<UserEntity> userEntityList = username.isPresent() ?
                userEntityMap.values().stream().filter(u -> u.getUsername().equalsIgnoreCase(username.get())).collect(Collectors.toList())
                : new ArrayList(userEntityMap.values());
        List<User> userList = modelMapper.map(userEntityList, new TypeToken<List<User>>() {
        }.getType());
        return userList;
    }

    @Override
    public void updateUser(long id, User user) {

        UserEntity existingUserEntity = userEntityMap.get(id);

        if (Objects.isNull(existingUserEntity)) {
            throw new NotFoundException("User Not Found");
        }
        // Advantage of modelMapper
        //UserEntity userEntity = new UserEntity();
        //userEntity.setFirstName(user.getFirstName());
        UserEntity userEntity = modelMapper.map(user, UserEntity.class);
        //FORCE
        userEntity.setId(id);
        userEntityMap.put(id, userEntity);


    }

    @Override
    public void patchUser(long id, JsonPatch jsonPatch) throws JsonPatchException, JsonProcessingException {

        UserEntity existingUserEntity = userEntityMap.get(id);

        if (Objects.isNull(existingUserEntity)) {
            throw new NotFoundException("User Not Found");
        }


        ObjectMapper objectMapper = mapperBuilder.build();
        JsonNode jsonNode = objectMapper.convertValue(existingUserEntity, JsonNode.class);
        JsonNode patchedUserNode = jsonPatch.apply(jsonNode);
        UserEntity patchedUserEntity = objectMapper.treeToValue(patchedUserNode, UserEntity.class);

        //FORCE
        patchedUserEntity.setId(id);
        userEntityMap.put(id, patchedUserEntity);


    }


    private String generateUsername(long id) {
        return usernamePrefix + id;
    }

}
