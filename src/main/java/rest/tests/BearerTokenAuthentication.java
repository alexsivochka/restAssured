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
            .userName("test_123")
            .password("Test@123")
//            .userID("04a4396c-df28-4766-b605-6156533bbb83")
            .build();

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();
    ResponseSpecification responseSpecification = new RequestSpecs().responseSpecSuccess();

    public String getToken(){
        JsonPath response =
                given()
                    .spec(requestSpecification)
                    .basePath("/Account/v1/GenerateToken")
                .when()
                    .body("{\n" +
                            "  \"userName\": \"test_123\",\n" +
                            "  \"password\": \"Test@123\"\n" +
                            "}")
                    .post()
                .then()
                    .spec(responseSpecification)
                    .extract().response().jsonPath();
        String token = response.getString("token");
        return token;
    }

    @Test(priority = 1)
    public void testAddBook() {

        given()
                .spec(requestSpecification)
                .basePath("/BookStore/v1/Books")
                .auth().oauth2(getToken())
                .log().headers()
        .when()
                .body("{\n" +
                        "  \"userId\": \"04a4396c-df28-4766-b605-6156533bbb83\",\n" +
                        "  \"collectionOfIsbns\": [\n" +
                        "    {\n" +
                        "      \"isbn\": \"9781449337711\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}")
                .post()
        .then()
                .statusCode(201)
                .extract().response().prettyPrint();
    }

    @Test(priority = 2)
    public void checkBookAddToUser(){

        String path = "/Account/v1/User/".concat("04a4396c-df28-4766-b605-6156533bbb83");

        given()
                .spec(requestSpecification)
                .basePath(path)
                .auth().oauth2(getToken())
        .when()
                .get()
        .then()
                .spec(responseSpecification)
                .body("books.isbn", hasItem("9781449337711"))
                .extract().response().body().prettyPrint();
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



}
