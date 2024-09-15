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

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "username",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();

    public Set<Authority> getAuthorities() {
        return authorities;
    }

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }

    public void addAuthority(String authorityString) {
        this.authorities.add(new Authority(this.username, authorityString));
    }

    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }
}