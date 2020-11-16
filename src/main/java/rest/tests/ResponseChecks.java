package rest.tests;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import rest.RequestSpecs;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ResponseChecks {

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();
    ResponseSpecification responseSpecification = new RequestSpecs().responseSpecSuccess();


    @Test(priority = 1)
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
                .extract().response().prettyPrint();
    }

    @Test(priority = 2)
    public void checkTotalBookAuthorsCount(){

        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Books")
                .when()
                .get()
                .then()
                .spec(responseSpecification)
                .body("books.author*.length()", hasSize(8))
                .extract().response().prettyPrint();
    }

    @Test(priority = 3)
    public void checkUserBookCount(){

        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Books")
        .when()
                .get()
        .then()
                .spec(responseSpecification)
                .body("books.findAll { it.pages > 400 }", hasSize(2))
                .extract().response().prettyPrint();
    }


    @Test(priority = 4)
    public void checkTotalBookPagesCount(){

        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Books")
        .when()
                .get()
        .then()
                .spec(responseSpecification)
                .body("books.collect { it.pages }.sum()", greaterThan(2500))
                .extract().response().prettyPrint();
    }

}
