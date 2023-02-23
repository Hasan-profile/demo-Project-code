package com.rest.app;

import jakarta.validation.Valid;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value =  "/book")
public class BookController {

    @Autowired
    BookRepository bookRepository;

    @GetMapping
    public List<Book> getAllBookRecords() {

        return bookRepository.findAll();
    }

    @GetMapping(value = "{bookId}")
    public Book getBookById(@PathVariable(value = "bookId") Long bookId) {
        return bookRepository.findById(bookId).get();
    }

    @PostMapping
    public Book createBookRecord(@RequestBody @Valid Book bookRecord) {

        return bookRepository.save(bookRecord);
    }



    @PutMapping
    public Book updateBookRecord(@RequestBody @Valid Book bookRecord) throws ChangeSetPersister.NotFoundException {
        if(bookRecord == null || bookRecord.getBookId() == null)
            throw new ChangeSetPersister.NotFoundException();
        return bookRecord;
    }

    Optional<Book> optionalBook = bookRepository.findbyId(bookRecord.getBookId());
    if(!optionalBook.isPresent()){
        throw new NotFoundException("Book with ID:"+ bookRecord.getBookId()+ "does not exists.");
    }

    Book existsBookRecord =optionalBook.get();
    existingBookRecord.setName(bookRecord.getName());
    existingBookRecord.setSummary(bookrecord.getSummary());
    existingBookRecord.setRating(bookRecord.getrating());

    return bookRepository.save(existingBookRecord);

    @DeleteMapping(value = "{bookId")
    public void deleteBookById(@PathVariable(value = "bookId")Long bookId) throws Exception{
        if(!bookRepository.findbyId(bookId).isPresent()) {
            throw new NotFoundException("bookId"+ "bookId" +"not Present");
        }
        bookRepository.deleteById(bookId);
    }
}
