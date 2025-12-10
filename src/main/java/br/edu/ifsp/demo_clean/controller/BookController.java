package br.edu.ifsp.demo_clean.controller;

import br.edu.ifsp.demo_clean.dto.BookRequestDTO;
import br.edu.ifsp.demo_clean.dto.response.BaseResponseDTO;
import br.edu.ifsp.demo_clean.dto.response.BookResponseDTO;
import br.edu.ifsp.demo_clean.service.BookService;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/library"})
@Tag(name = "Livros", description = "Responsável por controlar os livros")
public class BookController extends BaseController {
    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping({"/book"})
    public ResponseEntity<BaseResponseDTO<BookResponseDTO>> addBook(@RequestBody BookRequestDTO bookRequestDTO) {
        return handleRequest(() -> new BookResponseDTO(bookService.addBook(bookRequestDTO)));
    }

    @GetMapping({"/book/{isbn}"})
    public ResponseEntity<BaseResponseDTO<BookResponseDTO>> getByISBN(@PathVariable int isbn) {
        return handleRequest(() -> new BookResponseDTO(bookService.getBookByISBN(isbn)));
    }

    @PutMapping({"/book/{isbn}"})
    public ResponseEntity<BaseResponseDTO<BookResponseDTO>> updateBook(@RequestBody BookRequestDTO bookRequestDTO, int isbn) {
        return handleRequest(() -> new BookResponseDTO(bookService.updateBook(bookRequestDTO, isbn)));
    }

    @DeleteMapping({"/book/{isbn}"})
    public ResponseEntity<BaseResponseDTO<BookResponseDTO>> deleteBook(@PathVariable int isbn) {
        return handleRequest(() -> new BookResponseDTO(bookService.deleteBookByISBN(isbn)));
    }
}