package Models;

import models.User;

import javax.persistence.*;
import javax.ws.rs.core.Link;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tbl_kweet")
@NamedQueries({
        @NamedQuery(name="Kweet.getKweets",
                query="SELECT k FROM Kweet k"),
        @NamedQuery(name="Kweet.getKweet",
                query="SELECT k FROM Kweet k WHERE k.id = :kweetId"),
        @NamedQuery(name="Kweet.searchKweet",
                query="SELECT k FROM Kweet k WHERE k.message LIKE :message"),
        @NamedQuery(name="Kweet.getKweetByUser",
                query="SELECT k FROM Kweet k WHERE k.author.username = :username")

})
public class Kweet {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column
    private String message;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @OneToOne
    private User author;

    @OneToOne
    private User recipient;

    @Transient
    private List<Link> selfLinks = new ArrayList<>();

    public Kweet(){

    }

    public Kweet(String message, Date timestamp, User author, User recipient){
        this.message = message;
        this.timestamp = timestamp;
        this.author = author;
        this.recipient = recipient;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getRecipient() {
        return recipient;
    }

    public void setRecipient(User recipient) {
        this.recipient = recipient;
    }

    public List<Link> getSelfLinks() {
        return selfLinks;
    }

    public void setSelfLinks(List<Link> selfLinks) {
        this.selfLinks = selfLinks;
    }
}
