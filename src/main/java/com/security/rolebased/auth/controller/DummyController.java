package com.security.rolebased.auth.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class DummyController {

    @GetMapping("")
    public ResponseEntity<String> getFunction2(){
        return ResponseEntity.ok("Hello this has no routing ");
    }

    @GetMapping("/get")
    public ResponseEntity<String> getFunction(){
        return ResponseEntity.ok("Hello this is get routing ");
    }
}
