package it.unipd.mtss.model;

import java.time.LocalDate;
import java.time.Period;

public class User {
    private int id;
    private String name;
    private String surname;
    private String birthDate;

    User(int id, String name, String surname, String birthDate){
        this.id = id;
        this.name = name;
        this.surname = surname;
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

    public int getAge(){
        LocalDate curDate = LocalDate.now();

        return Period.between(LocalDate.parse(birthDate), curDate).getYears();
    }
}
