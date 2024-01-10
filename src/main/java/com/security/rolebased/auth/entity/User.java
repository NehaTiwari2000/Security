package com.security.rolebased.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    private String role;
}
