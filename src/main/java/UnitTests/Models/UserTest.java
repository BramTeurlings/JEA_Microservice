package UnitTests.Models;

import models.User;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class UserTest {
    User user;

    @Before
    public void Before(){
        this.user = new User();
        user.setUsername("test");
        user.setPassword("test2");
    }

    @Test
    public void getUsername() throws Exception {
        assertEquals("test", user.getUsername());
    }

    @Test
    public void setUsername() throws Exception {
        user.setUsername("test2");
        assertEquals("test2", user.getUsername());
    }

    @Test
    public void getPassword() throws Exception {
        assertEquals("test2", user.getPassword());
    }

    @Test
    public void setPassword() throws Exception {
        user.setPassword("test3");
        assertEquals("test3", user.getPassword());
    }

}