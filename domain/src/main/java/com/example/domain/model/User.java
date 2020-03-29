package com.example.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;

@Document
@ToString(exclude = "password")
@Getter
@Setter
@AllArgsConstructor
public class User {

    @Id
    private String id;

    private String username;

    private String firstName;

    private String lastName;

    @Email
    private String email;

    private String password;
}
