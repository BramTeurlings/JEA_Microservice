package Dao;

import Models.Kweet;
import models.User;

import java.util.List;

public interface ChatDao {
    List<Kweet> getKweets();
    List<Kweet> searchKweet(String term);
    void removeKweet(Kweet kweet);
    void addKweet(Kweet kweet);
    List<Kweet> getKweetsByUser(User user);
}
