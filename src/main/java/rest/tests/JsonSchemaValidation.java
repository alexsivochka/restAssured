package rest.tests;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import rest.RequestSpecs;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;

public class JsonSchemaValidation {

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();
    ResponseSpecification responseSpecification = new RequestSpecs().responseSpecSuccess();

    @Test
    public void validateResponseSchema(){
        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Book")
                .queryParam("ISBN", "9781449325862")
        .when()
                .get()
        .then()
                .spec(responseSpecification)
                .log().body()
                .body(matchesJsonSchema(new File("src/main/resources/book.json")));

    }



}
