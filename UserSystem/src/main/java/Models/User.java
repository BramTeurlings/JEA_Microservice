package Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.persistence.*;
import javax.ws.rs.core.Link;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@NamedQueries({
        @NamedQuery(name="User.getUsers",
                query="SELECT u FROM User u"),
        @NamedQuery(name="User.getUser",
                query="SELECT u FROM User u WHERE u.username = :id"),
        @NamedQuery(name="User.searchUser",
                query="SELECT u FROM User u INNER JOIN Profile p ON u.profile = p WHERE u.username LIKE :username"),
        @NamedQuery(name = "User.getUserByLoginPassword",
                query = "SELECT u FROM User u WHERE u.username = :username AND u.password = :password"),

})
@Named("user")
@SessionScoped
public class User implements Serializable {

    @Id
    @Column(unique = true, nullable = false)
    private String username;


    @JsonIgnore
    @Column
    private String password;

    @javax.persistence.Transient
    private List<Link> selfLinks = new ArrayList<>();

    public User(){

    }

    public User(String username){
        this.username = username;
    }

    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Models.User can register a new account
     * @return
     */
    public boolean register(String username, String password, String email, String Adress){
        throw new NotImplementedException();
    }

    /**
     * Models.User can log into the application
     * @param username
     * @param password
     * @return
     */
    public boolean login(String username, String password){
        throw new NotImplementedException();
    }

    /**
     * Models.User can log out
     * @return
     */
    public boolean logout(){
        throw new NotImplementedException();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Link> getSelfLinks() {
        return selfLinks;
    }

    public void setSelfLinks(List<Link> selfLinks) {
        this.selfLinks = selfLinks;
    }
}
