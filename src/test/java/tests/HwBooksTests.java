package tests;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("homework-13")
public class HwBooksTests extends BaseTest {

  @Test
  @DisplayName("Получение списка книг")
  void getAllBooksTest() {
    given()
        .contentType(JSON)
        .log().uri()

        .when()
        .get("/BookStore/v1/Books")

        .then()
        .log().body()
        .statusCode(200)
        .body("books[0].isbn", equalTo("9781449325862"))
        .body("books[0].title", equalTo("Git Pocket Guide"))
        .body("books[0].author", equalTo("Richard E. Silverman"))
        .body("books[0].pages", equalTo(234))
        .body("books[0].description", containsString("This pocket guide"))
        .body("books[0].website", containsString("chimera.labs.oreilly"));
  }

  @Test
  @DisplayName("Получение списка книг при помощи ISBN")
  void getBooksByIsbnTest() {
    given()
        .contentType(JSON)
        .log().uri()
        .queryParam("ISBN", "9781449325862")

        .when()
        .get("/BookStore/v1/Books")

        .then()
        .log().body()
        .statusCode(200)
        .body("books[0].isbn", equalTo("9781449325862"))
        .body("books[0].title", equalTo("Git Pocket Guide"))
        .body("books[0].author", equalTo("Richard E. Silverman"))
        .body("books[0].pages", equalTo(234))
        .body("books[0].description", containsString("This pocket guide"))
        .body("books[0].website", containsString("chimera.labs.oreilly"));
  }
}
