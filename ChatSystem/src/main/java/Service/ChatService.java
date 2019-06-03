package Service;

import Dao.ChatDao;
import Models.Kweet;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.Serializable;
import java.util.List;

@Stateless
public class ChatService implements Serializable {
    @Inject
    ChatDao kweetsDao;

    public List<Kweet> getKweets(){
        return kweetsDao.getKweets();
    }

    public void addKweet(Kweet kweet){
        kweetsDao.addKweet(kweet);
    }

    public void removeKweet(Kweet kweet){
        kweetsDao.removeKweet(kweet);
    }

    public List<Kweet> searchKweet(String term){
        return kweetsDao.searchKweet(term);
    }

    public List<Kweet> getKweetsByUser(String user){
        return kweetsDao.getKweetsByUser(user);
    }
}
