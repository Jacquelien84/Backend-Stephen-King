package nl.oudhoff.backendstephenking.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "bookcovers")
public class Bookcover {

    @Id
    private String fileName;

    public Bookcover(String fileName) {
        this.fileName = fileName;
    }

    public Bookcover() {
    }
}


