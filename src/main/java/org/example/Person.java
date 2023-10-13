package org.example;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Person {
    private int id;
    private String name;
    private String username;
    private String email;
    private Address address;
    private String phone;
    private String website;
    private Company company;

    @Data
    @Builder
    public static class Address {
        private String street;
        private String suite;
        private String city;
        private String zipcode;
        private Geo geo;


        @Data
        @Builder
        public static class Geo {
            private String lat;
            private String lng;
        }
    }
    @Data
    @Builder
    public static class Company {
        private String name;
        private String catchPhrase;
        private String bs;
    }
}


