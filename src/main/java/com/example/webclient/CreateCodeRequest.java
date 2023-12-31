package com.example.webclient;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CreateCodeRequest {
    private String title;
    private String body;
    private Integer userId;
}
