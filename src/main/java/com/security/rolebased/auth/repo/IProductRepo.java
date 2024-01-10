package com.security.rolebased.auth.repo;

import com.security.rolebased.auth.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProductRepo extends JpaRepository<Product,Integer> {

    List<Product> findByName(String name);
}
