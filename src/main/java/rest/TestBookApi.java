package rest;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class TestBookApi {

//    {
//        "userName": "test_123",
//            "password": "Test@123"
//    }

//    {
//        "userID": "04a4396c-df28-4766-b605-6156533bbb83",
//            "username": "test_123",
//            "books": []
//    }

    @Test
    public void testPet(){

        RequestSpecification requestSpecification = new RequestSpecs().requestSpec();
        ResponseSpecification responseSpecification = new RequestSpecs().responseSpecSuccess();

//        RestAssured.baseURI = "https://petstore.swagger.io";
//        RestAssured.basePath = "/v2/pet";
//        RestAssured.useRelaxedHTTPSValidation();
//        RestAssured.proxy
//                .withHost("proxy.pbank.com.ua")
//                .withPort(8080)
//                .withAuth("proxyLogin", "proxyPassword");

//        given()
//                .spec(requestSpecification)
//                .log().all()
//        .when()
//                .body(pet)
//                .post()
//        .then()
//                .spec(responseSpecification)
//                .body("id", equalTo(pet.getId()))
//                .body("name", equalTo(pet.getName()))
//                .log().body();


//        given()
//                .baseUri("https://bookstore.toolsqa.com/BookStore/v1/Books")
//                .accept(ContentType.JSON)
//                .log().all()
//        .when()
//                .get()
//        .then()
//                .statusCode(200)
//                .body("books.title[0]", is("Git Pocket Guide"))
//                .time(lessThan(5000L))
//                .log().body();

//        given()
//                .spec(requestSpecification)
//                .basePath("Books")
//                .log().all()
//        .when()
//                .get()
//        .then()
//                .spec(responseSpecification)
//                .body("books.title[0]", is("Git Pocket Guide"))
//                .body("books", hasSize(greaterThanOrEqualTo(1)))
//                .log().body();





    }

}
