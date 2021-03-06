package Dao;

import Models.Group;
import Models.User;

import java.util.List;

public interface UserDao {
    List<User> getUsers();
    void addUser(User user);
    void removeUser(String name);
    User getUser(String name);
    void createUser(User user, Group group);
    List<User> searchUsers(String username);
    User authenticate(String username, String password);
}
