package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class UserTest {
    User user1;
    User user2;

    @Before
    public void before(){
        user1 = new User(1, "Mario", "Rossi", "1990-05-01");
        user2 = new User(1, "Luigi", "Bianchi", "1985-05-01");
    }

    @Test
    public void getId(){
        assertEquals(1, user1.getId());    
    }

    @Test
    public void getName(){
        assertEquals("Mario", user1.getName());
    }

    @Test
    public void getSurname(){
        assertEquals("Rossi", user1.getSurname());
    }

    @Test
    public void getBirthDate(){
        assertEquals("1990-05-01", user1.getBirthDate());
    }

    @Test public void getAge(){
        assertEquals(32, user1.getAge());
        assertEquals(37, user2.getAge());
    }
}
