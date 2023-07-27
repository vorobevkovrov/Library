package ru.vorobev.controllers;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.vorobev.models.Person;
import ru.vorobev.servises.PeopleService;

@AllArgsConstructor
@Data
@RestController
@RequestMapping("/people")
public class PeopleController {
    private final PeopleService peopleService;

    @PostMapping
    public ResponseEntity<?> addPeoples(Person person) {
        System.out.println("ResponseEntity addPeoples");
        try {
            peopleService.addPeople(person);
            return ResponseEntity.ok().body("Person added");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error peoples not added");
        }
    }

    @GetMapping
    public ResponseEntity<?> getPeoples() {
        try {
            return ResponseEntity.ok().body(peopleService.findAllPeoples());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error peoples not found");
        }
    }
}
