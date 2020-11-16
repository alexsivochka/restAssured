package rest.tests;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import rest.RequestSpecs;
import rest.entity.User;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BasicAuthentication {

    User user = User.builder()
            .userName("test_123")
            .password("Test@123")
            .build();

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();
    ResponseSpecification responseSpecification = new RequestSpecs().responseSpecSuccess();

    @Test
    public void testAddBook(){

        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Books")
                .auth().preemptive().basic(user.getUserName(), user.getPassword())
                .log().headers()
        .when()
                .body("{\n" +
                        "  \"userId\": \"04a4396c-df28-4766-b605-6156533bbb83\",\n" +
                        "  \"collectionOfIsbns\": [\n" +
                        "    {\n" +
                        "      \"isbn\": \"9781593275846\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post()
        .then()
                .statusCode(201)
                .extract().response().prettyPrint();
    }

    @Test
    public void checkBookAddToUser(){

        String path = "/Account/v1/User/".concat("04a4396c-df28-4766-b605-6156533bbb83");

        given()
                .spec(requestSpecification)
                .basePath(path)
                .auth().preemptive().basic(user.getUserName(), user.getPassword())
        .when()
                .get()
        .then()
                .spec(responseSpecification)
                .body("books.isbn", hasItem("9781593275846"))
                .extract().response().body().prettyPrint();
    }

}
