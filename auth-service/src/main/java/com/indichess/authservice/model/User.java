package com.indichess.authservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private Long userId;
    private String username;
    private String emailId;
    private String password;
    private String pfpUrl;
    private String country;
    private Integer rating;
}
