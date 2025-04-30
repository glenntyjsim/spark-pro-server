package com.example.login.model;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "favourite")
public class Favourite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cleaner_id", nullable = false)
    private User cleaner;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "date_created")
    private Date date_created;

    public Favourite() {
    }

    public Favourite(User cleaner, User user, Date date_created) {
        this.cleaner = cleaner;
        this.user = user;
        this.date_created = date_created;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getCleaner() {
        return cleaner;
    }

    public void setCleaner(User cleaner) {
        this.cleaner = cleaner;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getDateCreated() {
        return date_created;
    }

    public void setDateCreated(Date date_created) {
        this.date_created = date_created;
    }
}
