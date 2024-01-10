package com.security.rolebased.auth.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;
    private String description;
}
