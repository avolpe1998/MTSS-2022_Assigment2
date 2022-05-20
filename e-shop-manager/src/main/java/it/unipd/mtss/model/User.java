////////////////////////////////////////////////////////////////////
// Andrea Volpe 2021904
// Riccardo Contin 1225416
////////////////////////////////////////////////////////////////////
package it.unipd.mtss.model;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.Period;

public class User {
    private int id;
    private String name;
    private String surname;
    private String birthDate;

    public User(int id, String name, String surname, String birthDate) 
        throws DateTimeException {
        this.id = id;
        this.name = name;
        this.surname = surname;

        LocalDate.parse(birthDate); //check if is valid date format
        this.birthDate = birthDate;
    }

    public int getId(){
        return id;
    }

    public String getName(){
        return name;
    }

    public String getSurname(){
        return surname;
    }

    public String getBirthDate(){
        return birthDate;
    }

    public int getAge() throws DateTimeException {
        LocalDate curDate = LocalDate.now();

        return Period.between(LocalDate.parse(birthDate), curDate).getYears();
    }
}
