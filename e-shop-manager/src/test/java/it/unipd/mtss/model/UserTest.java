package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import java.time.DateTimeException;

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
    public void testGetId(){
        assertEquals(1, user1.getId());    
    }

    @Test
    public void testGetName(){
        assertEquals("Mario", user1.getName());
    }

    @Test
    public void testGetSurname(){
        assertEquals("Rossi", user1.getSurname());
    }

    @Test
    public void testGetBirthDate(){
        assertEquals("1990-05-01", user1.getBirthDate());
    }

    @Test
    public void testGetAge(){
        assertEquals(32, user1.getAge());
        assertEquals(37, user2.getAge());
    }

    @Test(expected = DateTimeException.class)
    public void testWrongBirthDateFormat(){
        new User(1, "Luigi", "Bianchi", "198a5-05-01");
    }

    @Test(expected = DateTimeException.class)
    public void testEmptyBirthDateFormat(){
        new User(1, "Luigi", "Bianchi", "");
    }

    @Test(expected = DateTimeException.class)
    public void testBirthDateEuropeFormat(){
        new User(1, "Luigi", "Bianchi", "01-05-1985");
    }
}
