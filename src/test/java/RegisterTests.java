import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.ContentType.JSON;

import static org.hamcrest.Matchers.is;

public class RegisterTests {

    @BeforeAll
    static void beforeAll(){
        RestAssured.baseURI = "https://reqres.in/";
        RestAssured.basePath = "api/";
    }

    @Test
    void successfullRegistrationTest(){
        String authBody = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post(baseURI + basePath + "register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void wrongEmailRegistrationTest(){
        String authBody = "{\"email\": \"eve1.holt@reqres.in\",\"password\": \"pistol\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post(baseURI + basePath + "register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Note: Only defined users succeed registration"));
    }

    @Test
    void missingEmailRegistrationTest(){
        String authBody = "{\"email\": \"\",\"password\": \"1pistol\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post(baseURI + basePath + "register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing email or username"));
    }

    @Test
    void missingPasswordRegistrationTest(){
        String authBody = "{\"email\": \"eve.holt@reqres.in\", \"password\": \"\" }";

        given()
                .log().uri()
                .log().method()
                .log().body()
                .body(authBody)
                .contentType(JSON)
                .when()
                .post(baseURI + basePath + "register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void resourceNotFoundTest(){
        given()
                .log().uri()
                .log().method()
                .when()
                .get(baseURI + basePath + "unknown/23")
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}

