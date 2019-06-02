package Dao.Implementation;

import Dao.UserDao;
import Models.Group;
import models.User;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.ejb.Stateless;
import javax.enterprise.inject.Alternative;
import java.util.ArrayList;
import java.util.List;

@Alternative
@Stateless
public class UserImplementation implements UserDao
{
    private List<User> users = new ArrayList<User>();

    public List<User> getUsers() {
        return users;
    }

    public void addUser(User user) {
        this.users.add(user);
    }

    public void removeUser(String name) {
        for(User u : users){
            if(u.getUsername().equals(name)){
                users.remove(u);
                break;
            }
        }
    }

    @Override
    public User getUser(String name) {
        throw new NotImplementedException();
    }

    @Override
    public void createUser(User user, Group group) {
        throw new NotImplementedException();
    }

    @Override
    public List<User> searchUsers(String username) {
        throw new NotImplementedException();
    }
}
