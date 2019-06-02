package Dao.Implementation;

import Dao.ChatDao;
import Models.Kweet;
import REST.UserService;
import models.User;

import javax.ejb.Stateless;
import javax.enterprise.inject.Default;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ChatDaoDBImpl implements ChatDao {
    @PersistenceContext(unitName = "ChatPersistenceUnit")
    public EntityManager em;

    public UserService service = new UserService();

    public List<Kweet> getKweets() {
        List<Kweet> kweets = em.createNamedQuery("Kweet.getKweets", Kweet.class).getResultList();
        return kweets;
    }

    public User getAuthor(Kweet kweet) {
        Kweet newKweet = em.createNamedQuery("Kweet.getKweet", Kweet.class).setParameter("kweetId", kweet.getId()).getSingleResult();
        return newKweet.getAuthor();
    }

    public User getRecepient(Kweet kweet) {
        Kweet newKweet = em.createNamedQuery("Kweet.getKweet", Kweet.class).setParameter("kweetId", kweet.getId()).getSingleResult();
        return newKweet.getRecipient();
    }

    public List<Kweet> searchKweet(String term) {
        List<Kweet> kweets = em.createNamedQuery("Kweet.searchKweet", Kweet.class).setParameter("message", "%" + term + "%").getResultList();
        return kweets;
    }

    public List<User> getUsers() {
        return em.createNamedQuery("User.getUsers", User.class).getResultList();
    }

    public void removeKweet(Kweet kweet) {
        Kweet dbKweet = em.createNamedQuery("Kweet.getKweet", Kweet.class).setParameter("kweetId", kweet.getId()).getSingleResult();
        em.remove(dbKweet);
    }

    public void addKweet(Kweet kweet) {
        try{
            User user = service.getUserByUsername(kweet.getAuthor().getUsername());
            kweet.setAuthor(user);
            em.merge(kweet);
        }
        catch(Exception e){
            em.persist(kweet);
        }
    }

    public void addUser(User user) {
        em.persist(user);
    }


    @Override
    public List<Kweet> getKweetsByUser(User user) {
        List<Kweet> kweets = new ArrayList<Kweet>();
        try {
            kweets = em.createNamedQuery("Kweet.getKweetByUser", Kweet.class).setParameter("username", user.getUsername()).getResultList();
        } catch (Exception e) {
        }
        return kweets;
    }
}
