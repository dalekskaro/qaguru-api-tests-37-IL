package tests;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("homework-13")
public class HwReqresTests {

  @BeforeAll
  static void setUri() {
    RestAssured.baseURI = "https://reqres.in";
  }

  @Test
  @DisplayName("PATCH Изменение частичной информации о юзере")
  void patchUserTest() {
    String body = "{\"email\": \"email.patch@qa.test\"}";
    int userId = 1;

    given()
        .header("x-api-key", "reqres-free-v1")
        .contentType(JSON)
        .body(body)
        .log().uri()
        .when()
        .patch("/api/users/" + userId)
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .body("email", is("email.patch@qa.test"))
        .body("updatedAt", containsString("2025"));
  }

  @Test
  @DisplayName("PUT Изменение информации о юзере")
  void putUserTest() {
    String body = "{\"email\": \"Hamilton.Patch@qa.test\",\n"
        + "    \"first_name\": \"Hamilton\",\n"
        + "    \"last_name\": \"Patch\"\n}";
    int userId = 1;

    given()
        .header("x-api-key", "reqres-free-v1")
        .contentType(JSON)
        .body(body)
        .log().uri()
        .when()
        .put("/api/users/" + userId)
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .body("email", is("Hamilton.Patch@qa.test"))
        .body("first_name", is("Hamilton"))
        .body("last_name", is("Patch"))
        .body("updatedAt", containsString("2025"));
  }

  @Test
  @DisplayName("DELETE Удаление пользователя")
  void deleteUserTest() {
    int userId = 1;

    given()
        .header("x-api-key", "reqres-free-v1")
        .contentType(JSON)
        .log().uri()
        .when()
        .delete("/api/users/" + userId)
        .then()
        .log().status()
        .statusCode(204);
  }
}
