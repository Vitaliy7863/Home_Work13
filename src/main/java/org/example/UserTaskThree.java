package org.example;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserTaskThree {
    private int userId;
    private int id;
    private String title;
    private boolean completed;
}
