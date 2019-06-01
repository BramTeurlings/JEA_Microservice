package Dao;

import models.User;

public interface AuthenticationDao {
    User authenticate(String username, String password);

    User getUser(String name);
}
