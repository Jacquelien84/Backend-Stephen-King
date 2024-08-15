package nl.oudhoff.backendstephenking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "roles")
public class Role {
    @Id
    private String rolename;

    //De koppeling tussen user en role
    @ManyToMany(mappedBy = "roles")
    private Set<User> users;
}
