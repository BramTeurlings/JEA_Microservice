package Service;

import Dao.UserDao;
import Models.Group;
import Models.Kweet;
import Models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.management.relation.Role;
import java.io.Serializable;
import java.util.List;

import static javax.management.relation.Role.*;

@Stateless
public class KwetterService implements Serializable {
    @Inject
    UserDao userDao;

    public List<User> getUsers(){
        return userDao.getUsers();
    }

    public void addUser(User user){
        userDao.addUser(user);
    }

    public void registerUser(User user, Group group){userDao.createUser(user, group);}


    public void removeUser(User user){
        userDao.removeUser(user.getUsername());
    }

    public List<User> searchUser(String username){
        return userDao.searchUsers(username);
    }

    public User getUser(String name){
        try{
            return userDao.getUser(name);
        }catch (Exception e){
            return null;
        }
    }

    public User authenticateUser(String username, String password){
        return userDao.authenticate(username, password);
    }
}
