package Models;

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
                query="SELECT k FROM Kweet k WHERE k.author = :username")

})
public class Kweet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String message;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private Date timestamp;

    @Column
    private String author;

    @Column
    private String recipient;

    @Transient
    private List<Link> selfLinks = new ArrayList<>();

    public Kweet(){

    }

    public Kweet(String message, Date timestamp, String author, String recipient){
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

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public List<Link> getSelfLinks() {
        return selfLinks;
    }

    public void setSelfLinks(List<Link> selfLinks) {
        this.selfLinks = selfLinks;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
