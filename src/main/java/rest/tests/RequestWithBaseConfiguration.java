package rest.tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;

public class RequestWithBaseConfiguration {

    @BeforeMethod
    public void setBaseConfig(){
        RestAssured.baseURI = "https://bookstore.toolsqa.com";
        RestAssured.basePath = "BookStore/v1/Books";
        RestAssured.useRelaxedHTTPSValidation();
//        RestAssured.proxy
//                .withHost("proxy.pbank.com.ua")
//                .withPort(8080)
//                .withAuth("proxyLogin", "proxyPassword");
    }

    @Test
    public void testGetBooks(){

        given()
                .accept(ContentType.JSON)
                .log().headers()
        .when()
                .get()
        .then()
                .statusCode(200)
                .body("books.title[0]", is("Git Pocket Guide"))
                .time(lessThan(5000L))
                .log().body();
    }

}
