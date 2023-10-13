package org.example;

import com.google.gson.Gson;

import java.io.IOException;

import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        Gson gson = new Gson();
        Person person = Person.builder()
                .id(1)
                .name("Oleg")
                .username("oleg.pro")
                .email("OL.eg10@gmail.com")
                .address(new Person.Address("Operna", "Apt. 566",
                        "Lviv", "92998-999", Person.Address.Geo.builder().lat("-37.9999").lng("81.9999").build()))
                .phone("010-692-6593 x09125")
                .website("ramiro.info")
                .company(new Person.Company("Romaguera-Jacobson", "Face to face bifurcated interface", "e-enable strategic applications"))
                .build();

        MethodHTTP methodHTTP = new MethodHTTP();
        methodHTTP.createPerson(person);
        methodHTTP.update(2, person);
        System.out.println(methodHTTP.delete(1));
        System.out.println(methodHTTP.getData());
        System.out.println(methodHTTP.getPersonId(9));
        System.out.println(methodHTTP.getPersonUsername("Elwyn.Skiles"));

        methodHTTP.getComments(1);

        methodHTTP.openTasks(1);


    }
}