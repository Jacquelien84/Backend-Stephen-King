package nl.oudhoff.backendstephenking.model;

import jakarta.persistence.*;
import lombok.Data;

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
}
