package Dao.Implementation;

import Dao.ChatDao;
import Models.Kweet;
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

    public List<Kweet> getKweets() {
        List<Kweet> kweets = em.createNamedQuery("Kweet.getKweets", Kweet.class).getResultList();
        return kweets;
    }

    public String getAuthor(Kweet kweet) {
        Kweet newKweet = em.createNamedQuery("Kweet.getKweet", Kweet.class).setParameter("kweetId", kweet.getId()).getSingleResult();
        return newKweet.getAuthor();
    }

    public String getRecepient(Kweet kweet) {
        Kweet newKweet = em.createNamedQuery("Kweet.getKweet", Kweet.class).setParameter("kweetId", kweet.getId()).getSingleResult();
        return newKweet.getRecipient();
    }

    public List<Kweet> searchKweet(String term) {
        List<Kweet> kweets = em.createNamedQuery("Kweet.searchKweet", Kweet.class).setParameter("message", "%" + term + "%").getResultList();
        return kweets;
    }

    public void removeKweet(Kweet kweet) {
        Kweet dbKweet = em.createNamedQuery("Kweet.getKweet", Kweet.class).setParameter("kweetId", kweet.getId()).getSingleResult();
        em.remove(dbKweet);
    }

    public void addKweet(Kweet kweet) {
        try{
//            String user = service.getUserByUsername(kweet.getAuthor());
//            kweet.setAuthor(user);
            em.merge(kweet);
        }
        catch(Exception e){
            em.persist(kweet);
        }
    }


    @Override
    public List<Kweet> getKweetsByUser(String user) {
        List<Kweet> kweets = new ArrayList<Kweet>();
        try {
            kweets = em.createNamedQuery("Kweet.getKweetByUser", Kweet.class).setParameter("username", user).getResultList();
        } catch (Exception e) {
        }
        return kweets;
    }
}
