package org.example;

import lombok.Builder;
import lombok.Data;

//TaskTwo
@Data
@Builder
public class Comments {
    private int postId;
    private int id;
    private String name;
    private String email;
    private String body;
}
