package org.example;

import lombok.Builder;
import lombok.Data;

//TaskTwo
@Data
@Builder
public class Posts {
    private int userId;
    private int id;
    private String name;
    private String email;
    private String body;
}
