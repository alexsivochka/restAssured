package rest.tests;

import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import rest.RequestSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;

public class RequestWithSpecifications {

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();
    ResponseSpecification responseSpecification = new RequestSpecs().responseSpecSuccess();

    @Test
    public void testGetBooks(){

        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Books")
                .log().headers()
        .when()
                .get()
        .then()
                .spec(responseSpecification)
                .body("books.title[0]", is("Git Pocket Guide"))
                .log().all();
    }

}
