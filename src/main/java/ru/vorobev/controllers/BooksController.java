package ru.vorobev.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BooksController {
    @GetMapping
    public ResponseEntity getBooks() {
        System.out.println("ResponseEntity books");
        try {
            return ResponseEntity.ok("Book fiend");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error books not found");
        }
    }
}

