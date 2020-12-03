package rest.tests;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import rest.RequestSpecs;
import rest.entity.User;


import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BasicAuthentication {

    User user = User.builder()
            .userName("testTest904")
            .password("testTest123@")
            .build();

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();
    ResponseSpecification responseSpecification = new RequestSpecs().responseSpecSuccess();

    @Test(priority = 1)
    public void testAddNewUser(){
        Response response = given()
                .spec(requestSpecification)
                .basePath("/Account/v1/User")
        .when()
                .body(user)
                .post()
        .then()
                .statusCode(201)
                .log().body()
                .extract().response();
        user.setUserID(response.getBody().jsonPath().getString("userID"));
    }

    @Test(priority = 2)
    public void testAddBook(){

        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Books")
                .auth().preemptive().basic(user.getUserName(), user.getPassword())
        .when()
                .body("{\n" +
                        "  \"userId\": \""+ user.getUserID() + "\",\n" +
                        "  \"collectionOfIsbns\": [\n" +
                        "    {\n" +
                        "      \"isbn\": \"9781449325862\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post()
        .then()
                .statusCode(201)
                .extract().response().prettyPrint();
    }

    @Test(priority = 3)
    public void checkBookAddToUser(){

        String path = "/Account/v1/User/".concat(user.getUserID());

        given()
                .spec(requestSpecification)
                .basePath(path)
                .auth().preemptive().basic(user.getUserName(), user.getPassword())
        .when()
                .get()
        .then()
                .spec(responseSpecification)
                .body("books.isbn", hasItem("9781449325862"))
                .body("books[0].title", equalTo("Git Pocket Guide"))
                .extract().response().body().prettyPrint();
    }

}
