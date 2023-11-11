package com.example.webclient;

import lombok.Data;

@Data
public class CreateCodeResponse {
    private Integer id;
    private String title;
    private String body;
    private Integer userId;
}
