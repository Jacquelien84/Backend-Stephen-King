package nl.oudhoff.backendstephenking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "authorities")
public class Authority {

    @Id
    @Getter
    @Setter
    private String username;

    @Getter
    private String authority;

    public Authority(String username, String authority) {
        this.username = username;
        this.authority = authority;
    }

    public Authority() {

    }
}


