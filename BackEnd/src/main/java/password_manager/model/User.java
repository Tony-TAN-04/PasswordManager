package password_manager.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String pseudo;

    @Column(nullable = false)
    private String mdp;

    // Constructeur vide obligatoire pour JPA
    public User() {
    }

    // Constructeur pratique
    public User(String pseudo, String mdp) {
        this.pseudo = pseudo;
        this.mdp = mdp;
    }

    // GETTERS

    public Long getId() {
        return id;
    }

    public String getPseudo() {
        return pseudo;
    }

    public String getMdp() {
        return mdp;
    }

    // SETTERS

    public void setId(Long id) {
        this.id = id;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public void setMdp(String mdp) {
        this.mdp = mdp;
    }
}