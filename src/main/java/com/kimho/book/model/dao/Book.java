package com.kimho.book.model.dao;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotEmpty
    private String title;

    @NotEmpty
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date createdAt = new Date();

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updatedAt = new Date();

    @URL(protocol = "http")
    private String image;

    private boolean enabled = false;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private Set<Comment> comments;
}
