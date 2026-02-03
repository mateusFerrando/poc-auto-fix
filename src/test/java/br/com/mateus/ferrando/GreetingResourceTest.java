package br.com.mateus.ferrando;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.containsString;

@QuarkusTest
class GreetingResourceTest {
    @Test
    void testHelloEndpoint() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("Hello from Quarkus REST"));
    }

    @Test
    void testSqlInjection() {
        given()
          .when().get("/hello/users/' OR '1'='1")
          .then()
             .statusCode(200)
             .body(containsString("admin"))
             .body(containsString("user"));
    }

    @Test
    void testNormalQuery() {
        given()
          .when().get("/hello/users/admin")
          .then()
             .statusCode(200)
             .body(containsString("admin"))
             .body(containsString("admin@example.com"));
    }
}