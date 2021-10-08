package com.kimho.book.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @Email
    @NotEmpty
    private String email;

    private String password;

    private String firstName;
    private String lastName;

    private boolean enabled;

    @URL(protocol = "https")
    private String avatar;

    @ManyToOne
    @JoinColumn(name = "role_id")
    @NotNull
    private Role role;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    private String providerId;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "comments"})
    private List<Book> books;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @JsonIgnoreProperties({"user", "book"})
    private List<Comment> comments;

    public User(String email, String firstname, String lastname, String password) {
        this.enabled = true;
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.password = password;
    }

    public User() {
        this.enabled = true;
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