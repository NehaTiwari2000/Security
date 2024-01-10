package com.security.rolebased.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class RoleRouting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String role;
    private String routing;
}
