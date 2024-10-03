package nl.oudhoff.backendstephenking.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Data
@Entity
@Table(name = "books")
public class Book {
    @Id
    private long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String originalTitle;
    @Column(nullable = false)
    private long released;
    @Column(nullable = false)
    private String movieAdaptation;
    @Column(length = 5000, nullable = false)
    private String description;

    @OneToOne
    private Bookcover bookcover;

    @JsonBackReference(value="favouriteBooks")
    @ManyToMany (mappedBy = "favouriteBooks")
    private Set<User> favourites;
}
