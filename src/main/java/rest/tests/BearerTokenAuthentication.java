package rest.tests;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;
import rest.RequestSpecs;
import rest.entity.User;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class BearerTokenAuthentication {

    User user = User.builder()
            .userName("test_709")
            .password("Test@123")
            .build();

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();
    ResponseSpecification responseSpecification = new RequestSpecs().responseSpecSuccess();

    public String getToken(){
        JsonPath response =
                given()
                    .spec(requestSpecification)
                    .basePath("/Account/v1/GenerateToken")
                .when()
                    .body(user)
                    .post()
                .then()
                    .spec(responseSpecification)
                    .extract().response().jsonPath();
        return response.getString("token");
    }

    @Test(priority = 1)
    public void testAddNewUser(){
        Response response =
                given()
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
    public void testAddBook() {

        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Books")
                .auth().oauth2(getToken())
                .log().headers()
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
                .auth().oauth2(getToken())
        .when()
                .get()
        .then()
                .spec(responseSpecification)
                .body("books.isbn", hasItem("9781449325862"))
                .body("books[0].title", equalTo("Git Pocket Guide"))
                .extract().response().body().prettyPrint();
    }

    @Test(priority = 3)
    public void checkBookCount(){

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



}
