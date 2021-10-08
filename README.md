# kimho-book-management
- Introduction:
    Project use spring Rest, Data Jpa, Security, Jwt, Oauth2, Lombok, Swagger, PostgreSQL
- How to set up:
    application.properties: Config when deploy to heroku, you can remove 
    application.yml: you change
        spring:
            datasource:
                url:
                username:
                password:
    build: ./gradlew build
    run: ./gradlew run
- How to use:
    Guest: View books, comments
    User: View, add, edit, delete my books, my comments
    Admin: View add, edit, delete my books, my comments, User's books, enable User
    SuperAdmin: all role
- Swagger:    
    http://localhost:8080/swagger-ui/index.html
    http://localhost:8080/v3/api-docs
