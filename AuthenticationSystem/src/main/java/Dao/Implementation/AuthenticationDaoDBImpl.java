package Dao.Implementation;

import Dao.AuthenticationDao;
import Authentication.PasswordUtils;
import models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.io.Serializable;

@Stateless
public class AuthenticationDaoDBImpl implements AuthenticationDao, Serializable{
    @PersistenceContext(unitName="NewPersistenceUnit")
    public EntityManager em;


    @Override
    public User authenticate(String username, String password) {
        TypedQuery<User> query = em.createNamedQuery("User.getUserByLoginPassword", User.class);
        query.setParameter("username", username);
        query.setParameter("password", PasswordUtils.digestPassword(password));
        return query.getSingleResult();
    }

    public User getUser(String name){
        try{
            return em.createNamedQuery("User.getUser", User.class).setParameter("id", name).getSingleResult();
        }catch (Exception e){}
        return null;
    }
}
