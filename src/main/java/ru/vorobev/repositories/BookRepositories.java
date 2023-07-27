package ru.vorobev.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.vorobev.models.Book;
import ru.vorobev.models.Person;

import java.util.List;

@Repository
public interface BookRepositories extends JpaRepository<Book, Integer> {

}
