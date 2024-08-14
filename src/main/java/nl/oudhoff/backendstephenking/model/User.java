package nl.oudhoff.backendstephenking.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;

    //De koppeling tussen user en review
    @OneToMany(mappedBy = "user")
    private List<Review> listOfReviews = new ArrayList<>();
}
