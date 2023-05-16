package hw18_reqres.in;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static hw18_reqres.in.Specs.*;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class ReqresInTestsHW {

    @Test
    @DisplayName("Проверка успешного создания пользователя")
    void testPostCreate() {
        String body = "{ \"name\": \"Testovui\", \"job\": \"QA\" }";

        given()
                .spec(request)
                .body(body)
                .when()
                .post("/users")
                .then()
                .log().all()
                .spec(responseSpec201)
                .body("name", is("Testovui"))
                .body("job", is("QA"))
                .body("id", is(notNullValue()))
                .body("createdAt", is(notNullValue()));
    }

    @Test
    @DisplayName("Проверка успешного удаления")
    void testDelete() {
        given()
                .spec(request)
                .when()
                .delete("/users/2")
                .then()
                .log().all()
                .spec(responseSpec204);
    }

    @Test
    @DisplayName("Проверка успешного редактирования пользователя")
    void testPutUpdate() {
        String body = "{ \"name\": \"TestovuiTest\", \"job\": \"QA Automation\" }";

        given()
                .spec(request)
                .body(body)
                .when()
                .put("/users/2")
                .then()
                .spec(responseSpec200)
                .log().all()
                .body("name", is("TestovuiTest"))
                .body("job", is("QA Automation"))
                .body("updatedAt", is(notNullValue()));
    }

    @Test
    @DisplayName("Проверка, что пользователь не найден")
    void testSingleUserNotFound() {
        given()
                .spec(request)
                .when()
                .get("/users/23")
                .then()
                .spec(responseSpec404)
                .log().all()
                .body(is("{}"));
    }

    @Test
    @DisplayName("Проверка JsonScheme")
        //https://www.liquid-technologies.com/online-json-to-schema-converter
    void testSingleResource() {
        given()
                .spec(request)
                .when()
                .get("/unknown/2")
                .then()
                .spec(responseSpec200)
                .log().all()
                .body(matchesJsonSchemaInClasspath("scheme/jsonscheme_resp.json"));
    }

    @Test
    @DisplayName("Проверка StatusCode при отправке запроса без body")
    void unSuccessTestPostCreate2() {
        given()
                .spec(request)
                .when()
                .post("/users")
                .then()
                .spec(responseSpec201)
                .log().all();
    }

    @Test
    @DisplayName("Проверка неудачной регистрации")
    void testRegisterUnsuccessful() {
        String body = "{ \"email\": \"incorrectemail@\" }";

        given()
                .spec(request)
                .body(body)
                .when()
                .post("/register")
                .then()
                .log().all()
                .spec(responseSpec400)
                .body("error", is("Missing email or username"));
    }
}
