package com.avalburo.backend.apirest.dao;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.avalburo.backend.apirest.entities.User;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class UserDao {
	private static final String FILE_PATH = "users.json";
    private ObjectMapper objectMapper = new ObjectMapper();
    
    private List<User> users;

    private int getNextId(List<User> users) {
        int maxId = users.stream().mapToInt(User::getId).max().orElse(0);
        return maxId + 1;
    }
    
    public List<User> findAll() {
        try {
            users = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<User>>() {});
            return users;
        } catch (IOException e) {
            e.printStackTrace();
            users = null;
            return new ArrayList<>();
        }
    }

    public User findById(int id) {
        try {
            users = objectMapper.readValue(new File(FILE_PATH), new TypeReference<List<User>>() {});
            Optional<User> optionalUser = users.stream().filter(user -> user.getId() == id).findFirst();
            return optionalUser.orElse(null);
        } catch (IOException e) {
            users = null;
            e.printStackTrace();
        }
        return null;
    }
    
    public User save(User user) {
        List<User> users = findAll();
        int nextId = getNextId(users);
        user.setId(nextId);
        LocalDateTime currentDateTime = LocalDateTime.now();
        ZonedDateTime ecuadorDateTime = currentDateTime.atZone(ZoneId.systemDefault());
        Timestamp currentTimestamp = Timestamp.from(ecuadorDateTime.toInstant());
        user.setCreatedAt(currentTimestamp);
        users.add(user);
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(FILE_PATH), users);
            return user;
        } catch (IOException e) {
            users = null;
            e.printStackTrace();
        }
        return null;
    }
}
