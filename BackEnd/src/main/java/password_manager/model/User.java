package password_manager.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private List<Website> websites = new ArrayList<>();

    // Constructeur vide obligatoire pour JPA
    public User() {
    }

    // Constructeur pratique
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // GETTERS

    public Integer getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Website> getWebsites() {
        return websites;
    }

    // SETTERS

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setWebsites(List<Website> websites) {
        this.websites = websites;
    }
}