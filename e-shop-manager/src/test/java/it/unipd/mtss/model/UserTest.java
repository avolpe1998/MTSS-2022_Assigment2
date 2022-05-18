package it.unipd.mtss.model;

import static org.junit.Assert.assertEquals;

import java.time.DateTimeException;

import org.junit.Test;

public class UserTest {

    @Test
    public void testGetId(){
        User user = new User(1, "Mario", "Rossi", "1990-05-01");

        int id = user.getId();

        assertEquals(1, id);    
    }

    @Test
    public void testGetName(){
        User user = new User(1, "Mario", "Rossi", "1990-05-01");

        String name = user.getName();

        assertEquals("Mario", name);
    }

    @Test
    public void testGetSurname(){
        User user = new User(1, "Mario", "Rossi", "1990-05-01");

        String surname = user.getSurname();

        assertEquals("Rossi", surname);
    }

    @Test
    public void testGetBirthDate(){
        User user = new User(1, "Mario", "Rossi", "1990-05-01");

        String birthDate = user.getBirthDate();

        assertEquals("1990-05-01", birthDate);
    }

    @Test
    public void testGetAge(){
        User user1 = new User(1, "Mario", "Rossi", "1990-05-01");
        User user2 = new User(2, "Luigi", "Bianchi", "1985-05-01");

        int ageUser1 = user1.getAge();
        int ageUser2 = user2.getAge();

        assertEquals(32, ageUser1);
        assertEquals(37, ageUser2);
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
