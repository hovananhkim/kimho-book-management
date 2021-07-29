package com.kimho.book.model.dao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

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

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties({"user", "comments"})
    private Set<Book> books;

    @OneToMany(mappedBy = "user")
    @JsonIgnoreProperties("user")
    private Set<Comment> comments;

    public User(String email, String firstname, String lastname, String password) {
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.password = password;
    }
}