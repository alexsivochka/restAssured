package rest.tests;

import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class SimpleRequest {

    @Test
    public void testGetBooks(){

        given()
                .baseUri("https://bookstore.toolsqa.com/BookStore/v1/Books")
                .accept(ContentType.JSON)
                .log().headers()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("books.title[0]", is("Git Pocket Guide"))
                .time(lessThan(5000L))
                .log().body();
    }

}
