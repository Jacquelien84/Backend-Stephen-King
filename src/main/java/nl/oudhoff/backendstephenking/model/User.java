package nl.oudhoff.backendstephenking.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(nullable = false, unique = true)
    private String username;
    private String email;
    @Column(nullable = false, length = 250)
    private String password;
    @Column
    private String apikey;

    @OneToMany(mappedBy = "user")
    private List<Review> listOfReviews = new ArrayList<>();

    @ManyToMany(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<Role> roles = new HashSet<>();

    public enum Role {
        ADMIN, MODERATOR, USER;
    }

}