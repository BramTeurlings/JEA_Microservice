package Dao.Implementation;

import models.AuthenticationUtils;
import Dao.UserDao;
import Models.Group;
import models.PasswordUtils;
import Models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

@Default
@Stateless
public class UserDaoDBImpl implements UserDao, Serializable{
    @PersistenceContext(unitName="NewPersistenceUnit")
    private EntityManager em;

    public List<User> getUsers() {
        return em.createNamedQuery("User.getUsers", User.class).getResultList();
    }

    public void addUser(User user) {
        em.persist(user);
    }

    public void removeUser(String name) {
        User dbUser = em.createNamedQuery("User.getUser", User.class).setParameter("id", name).getSingleResult();
        em.remove(dbUser);
    }

    public User getUser(String name){
        try{
            return em.createNamedQuery("User.getUser", User.class).setParameter("id", name).getSingleResult();
        }catch (Exception e){}
        return null;
    }

    public void createUser(User user, Group group) {
        try {
            user.setPassword(AuthenticationUtils.encodeSHA256(user.getPassword()));
        } catch (Exception e) {
            Logger.getLogger(getClass().getName()).log(Level.SEVERE, null, e);
            e.printStackTrace();
        }
        group.setUsername(user.getUsername());
        em.persist(user);
        em.persist(group);
    }

    @Override
    public List<User> searchUsers(String username) {
        try{
            return em.createNamedQuery("User.searchUser", User.class).setParameter("username", "%" + username + "%").getResultList();
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public User authenticate(String username, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.getUserByLoginPassword", User.class);
        query.setParameter("username", username);
        query.setParameter("password", PasswordUtils.digestPassword(password));
        return query.getSingleResult();
    }
}
