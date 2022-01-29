# spring-boot-read-eml-files-attachment



This Project is teaches how to read eml-files attachments

## Installation
We define these dependencies in pom.xml(This dependencies is very important!!!)
```java
  <dependency>
            <groupId>com.sun.mail</groupId>
            <artifactId>javax.mail</artifactId>
            <version>${javax.mail.version}</version>
        </dependency>

 <dependency>
            <groupId>commons-io</groupId>
            <artifactId>commons-io</artifactId>
            <version>2.7</version>
        </dependency>
```



## Usage

- Spring Boot
- Spring Boot Web
- Lombok(Optional)
- Apache Commons
- Swagger-OpenAPI

---
#### For invalid mapping pattern detected: /**/swagger-ui/** error when adding Swagger dependency
```application.properties
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
```
---
## License
[Muharrem Koç](https://github.com/muharremkoc)
