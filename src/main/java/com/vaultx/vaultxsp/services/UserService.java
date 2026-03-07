package com.vaultx.vaultxsp.services;

import com.vaultx.vaultxsp.models.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserid(String id);
    User updateUser(String id, User user);
    User createUser(User user);
    void deleteUser(String id);
}
