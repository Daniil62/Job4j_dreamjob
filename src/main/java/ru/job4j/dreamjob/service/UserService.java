package ru.job4j.dreamjob.service;

import org.springframework.stereotype.Service;
import ru.job4j.dreamjob.model.User;
import ru.job4j.dreamjob.store.UserDBStore;

import java.util.Optional;

@Service
public class UserService {

    private final UserDBStore store;

    public UserService(UserDBStore store) {
        this.store = store;
    }

    public User findUserByEmail(String email) {
        return store.findByEmail(email);
    }

    public Optional<User> add(User user) {
        return store.add(user);
    }

    public boolean deleteUser(String email, String password) {
        return store.deleteUser(email, password);
    }
}
