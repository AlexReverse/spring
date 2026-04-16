package com.merion.spring.book.service;

import com.merion.spring.base.exception.ResourceNotFoundException;
import com.merion.spring.book.entity.BookEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookService {
    static List<BookEntity> bookStorage = new ArrayList<>();

    public BookService() {
        fillBook();
    }

    public void fillBook() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            BookEntity book = new BookEntity();
            book.setId(i);
            book.setTitle("Book #" + random.nextInt(100, 999));
            book.setDescription("Spring Boot makes it easy to create stand-alone, production-grade Spring based Applications that you can 'just run'.");
            bookStorage.add(book);
        }
    }

    public List<BookEntity> all() {
        return bookStorage;
    }

    public Optional<BookEntity> byId(Integer id) {
        return bookStorage.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    public BookEntity create(String title, String description) {
        BookEntity book = new BookEntity();
        book.setId(bookStorage.size());
        book.setTitle(title);
        book.setDescription(description);
        bookStorage.add(book);
        return book;
    }

    public Optional<BookEntity> edit(BookEntity book) {
        BookEntity oldBookEntity = byId(book.getId()).orElseThrow(ResourceNotFoundException::new);
        oldBookEntity.setTitle(book.getTitle());
        oldBookEntity.setDescription(book.getDescription());
        return Optional.of(oldBookEntity);
    }

    public Boolean delete(Integer id) {
        Optional<BookEntity> book = byId(id);
        return book.map(bookEntity -> bookStorage.remove(bookEntity)).orElse(false);
    }

    public Optional<BookEntity> update(Integer id, Map<String, String> fields) {
        BookEntity book = byId(id).orElseThrow(ResourceNotFoundException::new);

        for(String key : fields.keySet()) {
            switch (key) {
                case "title" -> book.setTitle(fields.get(key));
                case "description" -> book.setDescription(fields.get(key));
            }
        }

        return Optional.of(book);
    }
}
