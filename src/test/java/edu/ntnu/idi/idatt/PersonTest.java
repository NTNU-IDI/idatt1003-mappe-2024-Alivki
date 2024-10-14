package edu.ntnu.idi.idatt;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class PersonTest {

    @Test
    void getAge() {
        Person person = new Person("Aleks", 21);
        
        assertEquals(person.getAge(), 21);
    }
}