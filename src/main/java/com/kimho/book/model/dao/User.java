package com.kimho.book.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Email
    private String email;

    @NotBlank
    private String password;

    private String firstName;
    private String lastName;

    private boolean enabled = false;

    @URL(protocol = "http")
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "comments"})
    private List<Book> books;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "book"})
    private List<Comment> comments;

    public User(String email, String firstname, String lastname, String password) {
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.password = password;
    }

    public boolean isSuperAdmin() {
        return (this.getRole().getName().equals("ROLE_SUPER_ADMIN"));
    }

    public boolean isAdmin() {
        return (this.getRole().getName().equals("ROLE_ADMIN"));
    }

    public boolean isUser() {
        return (this.getRole().getName().equals("ROLE_USER"));
    }
}