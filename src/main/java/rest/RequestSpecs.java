package rest;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.hamcrest.Matchers.lessThan;

public class RequestSpecs {

    public RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setRelaxedHTTPSValidation()
                .setBaseUri("https://bookstore.toolsqa.com")
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
//                .setProxy(host("proxy.pbank.com.ua").withPort(8080).withAuth("proxyLogin", "proxyPassword"))
                .build();
    }

    public ResponseSpecification responseSpecSuccess() {
        return new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectResponseTime(lessThan(5000L))
                .build();
    }
}
