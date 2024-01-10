package com.security.rolebased.auth.controller;

import com.security.rolebased.auth.entity.Product;
import com.security.rolebased.auth.repo.IProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private IProductRepo productRepo;

    @GetMapping("fetch-all-product")
    public ResponseEntity<?> fetchAllProduct() {
        return ResponseEntity.status(HttpStatus.OK).body("list is fetched .." + this.productRepo.findAll());
    }

    @PostMapping("add-product")
    public ResponseEntity<?> addProduct(Product product) {
        return ResponseEntity.ok("Data saved");
    }

    @GetMapping("fetch-product-detail-by-product-name")
    public ResponseEntity<?> fetchProductDetailByProductName(String name) {
        List<Product> productList = this.productRepo.findByName(name);
        return ResponseEntity.ok("Product details are " + productList);
    }


}
