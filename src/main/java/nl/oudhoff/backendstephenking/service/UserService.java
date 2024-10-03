package nl.oudhoff.backendstephenking.service;


import nl.oudhoff.backendstephenking.dto.mapper.UserMapper;
import nl.oudhoff.backendstephenking.dto.output.UserOutputDto;
import nl.oudhoff.backendstephenking.model.Book;
import nl.oudhoff.backendstephenking.model.User;
import nl.oudhoff.backendstephenking.repository.BookRepository;
import nl.oudhoff.backendstephenking.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepo;
    private final BookRepository bookRepo;


    public UserService(UserRepository userRepo, BookRepository bookRepo) {
        this.userRepo = userRepo;
        this.bookRepo = bookRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    public UserOutputDto get(String username) {
        Optional<User> model = userRepo.findByUsername(username);
        if (model.isPresent()) {
            return UserMapper.fromModelToOutputDto(model.get());
        } else {
            throw new UsernameNotFoundException(username);
        }
    }

    public List<UserOutputDto> getAll() {
        List<User> models = userRepo.findAll();
        List<UserOutputDto> userOutputDto = new ArrayList<>();

        for (User u : models) {
            userOutputDto.add(UserMapper.fromModelToOutputDto(u));
        }

        return userOutputDto;
    }

    public Optional<User> assignBookToFavourites(String userId, Long bookId) {
        Optional<User> user = userRepo.findByUsername(userId);
        Optional<Book> book = bookRepo.findById(bookId);

        if (book.isPresent() && user.isPresent()) {
            User userEntity = user.get();
            Book bookEntity = book.get();

            if (!userEntity.getFavouriteBooks().contains(bookEntity)) {
                userEntity.getFavouriteBooks().add(bookEntity);
                bookEntity.getFavourites().add(userEntity);

                userRepo.save(userEntity);
                bookRepo.save(bookEntity);
            }
            return Optional.of(userEntity);
        } else {
            return Optional.empty();
        }
    }

    public Optional<User> removeBookFromFavourites(String userId, Long bookId) {
        Optional<User> user = userRepo.findByUsername(userId);
        Optional<Book> book = bookRepo.findById(bookId);

        if (book.isPresent() && user.isPresent()) {
            User userEntity = user.get();
            Book bookEntity = book.get();

            userEntity.getFavouriteBooks().remove(bookEntity);
            bookEntity.getFavourites().remove(userEntity);

            userRepo.save(userEntity);
            bookRepo.save(bookEntity);

            return Optional.of(userEntity);
        } else {
            return Optional.empty();
        }
    }
}

