package rest.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rest.RequestSpecs;
import rest.entity.User;

import static io.restassured.RestAssured.given;

public class AllureAttachment {

    @BeforeMethod
    public void addAllureFilter(){
        RestAssured.filters(new AllureRestAssured());
    }

    User user = User.builder()
            .userName("testTest094")
            .password("testTest123@")
            .build();

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();

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
}
