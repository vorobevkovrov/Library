package ru.vorobev.servises;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vorobev.models.Person;
import ru.vorobev.repositories.PeopleRepository;

import java.util.List;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class PeopleService {
    private final PeopleRepository peopleRepository;

    @Transactional
    public List<Person> findAllPeoples() {
        return peopleRepository.findAll();
    }

    @Transactional
    public Person addPeople(Person person) {
        return peopleRepository.save(person);
    }
}
