# Workshop GraphQL

This project is based on [https://github.com/eh3rrera/graphql-java-spring-boot-example](https://github.com/eh3rrera/graphql-java-spring-boot-example)

## Starting the Spring Boot web app
`mvnw spring-boot:run`

## Web UIs
### GraphiQL
Go to GraphiQL [http://localhost:8080/graphiql](http://localhost:8080/graphiql) to start executing queries. For example:
```
{
  findAllBooks {
    id
    isbn
    title
    pageCount
    authors {
      firstName
      lastName
    }
  }
}
```

Or:
```
mutation {
  newBook(
    title: "Java: The Complete Reference, Tenth Edition", 
    isbn: "1259589331", 
    authors: 1) {
      id title
  }
}
```

### Database admin
You can go to [http://localhost:8080/h2-console/](http://localhost:8080/h2-console) and enter the following information:
- JDBC URL: jdbc:h2:mem:testdb
- User Name: sa
- Password: <blank>



# License
MIT
