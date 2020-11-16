package rest.tests;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.specification.ProxySpecification;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import rest.RequestSpecs;
import rest.entity.User;

import static io.restassured.RestAssured.given;
import static io.restassured.specification.ProxySpecification.host;

public class AllureAttachment {

    RequestSpecification requestSpecification = new RequestSpecs().requestSpec();



    @BeforeMethod
    public void addAllureFilter(){
        RestAssured.filters(new AllureRestAssured());
        RestAssured.proxy = host("localhost").withPort(8080).withAuth("ldap", "pass");
    }

    @Test
    public void addNewUserTest(){
        User user = User.builder().userName("bla-bla998").password("Test@123").build();

        User createdUser =
                given()
                    .spec(requestSpecification)
                    .basePath("/Account/v1/User")
                    .auth().preemptive().basic("test_123", "Test@123")
                    .log().headers()
                .when()
                    .body(user)
                    .post()
                .then()
                    .statusCode(201)
                    .extract().response().as(User.class);

        Assert.assertEquals(user.getUserName(), createdUser.getUserName());
    }

}
