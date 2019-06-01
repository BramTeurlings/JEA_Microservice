package Service;

import Dao.AuthenticationDao;
import models.User;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;

@Stateless
public class AuthenticationService implements Serializable {
    @Inject
    AuthenticationDao userDao;

    public User authenticateUser(String username, String password){
        return userDao.authenticate(username, password);
    }

    public User getUser(String name){
        try{
            return userDao.getUser(name);
        }catch (Exception e){
            return null;
        }
    }
}
