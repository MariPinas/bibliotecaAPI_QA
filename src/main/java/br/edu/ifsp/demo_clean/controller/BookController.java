package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.BookDTO;
import br.edu.ifsp.demo_clean.model.Book;
import br.edu.ifsp.demo_clean.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/library"})
@Tag(name = "Livros", description = "Responsável por controlar os livros")
public class BookController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping({"/book"})
    public ResponseEntity<Book> addBook(@RequestBody BookDTO bookDTO) {
        try{
            return ResponseEntity.ok(this.bookService.addBook(bookDTO));
        } catch (Error ex){
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @GetMapping({"/book/{isbn}"})
    public ResponseEntity<Book> getByISBN(@PathVariable int isbn) {
        try {
            return ResponseEntity.ok(bookService.getBookByISBN(isbn));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @PutMapping({"/book/{isbn}"})
    public ResponseEntity<Book> updateBook(@RequestBody BookDTO bookDTO, int isbn) {
        try {
            return ResponseEntity.ok(bookService.updateBook(bookDTO, isbn));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }

    @DeleteMapping({"/book/{isbn}"})
    public ResponseEntity<Book> deleteBook(@PathVariable int isbn) {
        try {
            return ResponseEntity.ok(bookService.deleteBookByISBN(isbn));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(null);
        }
    }
}