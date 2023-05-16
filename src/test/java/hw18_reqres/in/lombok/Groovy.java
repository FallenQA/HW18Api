package hw18_reqres.in.lombok;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
public class Groovy {
    @Test
    @DisplayName("Проверка получения корректных данных по определенному пользователю")
    public void testSingleUserGroovy() {
        // @formatter:off
        given()
                .spec(Specs.request)
                .when()
                .get("/users")
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("emma.wong@reqres.in"))
                .body("data.findAll{it.first_name =~/[a-zA-Z]+/}.first_name.flatten()",
                        hasItem("Emma"))
                .body("data.findAll{it.last_name =~/[a-zA-Z]+/}.last_name.flatten()",
                        hasItem("Wong"));
        // @formatter:on
    }
}
