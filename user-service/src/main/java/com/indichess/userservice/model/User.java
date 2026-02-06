package com.indichess.userservice.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Size(min = 1, max = 50, message = "Username must have characters between 1 and 50")
    @Column(name = "user_name", unique = true)
    private String username;

    @Column(name = "email_id", unique = true)
    @Email
    private String emailId;

    @Size(min = 2, max = 512)
    private String password;

    private String pfpUrl;

    private String country;

    private Integer rating;
}
