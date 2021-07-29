CREATE TABLE role (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    UNIQUE (name)
);

CREATE TABLE users (
    id SERIAl PRIMARY KEY,
    email VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    firstname VARCHAR(255) 
    lastname VARCHAR(255),
    role_id INT NOT NULL,
    enabled BOOLEAN DEFAULT FALSE,
    avatar VARCHAR(255)
    FOREIGN KEY(role_id)  REFERENCES role(id),
    UNIQUE(email)
);

CREATE TABLE book(
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    image VARCHAR(255),
    enabled BOOLEAN DEFAULT FALSE,
    user_id INT NOT NULL,
    UNIQUE(title),
    FOREIGN KEY(user_id) REFERENCES users(id)
);

CREATE TABLE comment(
    id SERIAL PRIMARY KEY,
    message TEXT NOT NULL,
    user_id INT NOT NULL,
    book_id INT NOT NULL
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY(user_id) REFERENCES users(id)
    FOREIGN KEY(book_id) REFERENCES book(id)
);

INSERT INTO role(name) VALUES("ROLE_SUPER_ADMIN");
INSERT INTO role(name) VALUES("ROLE_ADMIN");
INSERT INTO role(name) VALUES("ROLE_USER");

INSERT INTO users(email, password, role_id) VALUES("hvakim@gmail.com","password",3);
INSERT INTO users(email, password, role_id) VALUES("admin@gmail.com","password",2);
INSERT INTO users(email, password, role_id) VALUES("superadmin@gmail.com","password",1);

INSERT INTO book(title, author, user_id) VALUES("Tieng Viet 1","NXBGDVDT",1);
INSERT INTO book(title, author, user_id) VALUES("Tieng Viet 2","NXBGDVDT",2);
INSERT INTO book(title, author, user_id) VALUES("Tieng Viet 3","NXBGDVDT",3);

INSERT INTO comment(message, user_id, book_id) VALUES("Sach hay",1,1);
INSERT INTO comment(message, user_id, book_id) VALUES("Sach hay",2,3);
INSERT INTO comment(message, user_id, book_id) VALUES("Sach hay",3,2);