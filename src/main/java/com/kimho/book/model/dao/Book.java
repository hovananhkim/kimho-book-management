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
import java.util.List;

@Getter
@Setter
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
    private Date createdAt ;

    @NotNull
    @JsonFormat(pattern="yyyy-MM-dd HH:mm")
    private Date updatedAt ;

    @URL(protocol = "https")
    private String image;

    private boolean enabled ;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Comment> comments;

    public Book() {
        this.enabled = false;
        this.createdAt = new Date();
        this.updatedAt = new Date();
    }
}
