package tests;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import org.junit.jupiter.api.Test;

public class StatusTests {
    /*
    1. Make request to https://selenoid.autotests.cloud/status
    2. Get response { total: 20, used: 0, queued: 0, pending: 0, browsers: ...
    3. Check total is 20
     */

  @Test
  void checkTotal20() {
    get("https://selenoid.autotests.cloud/status")
        .then()
        .body("total", is(5));
  }

  @Test
  void checkTotalWithResponseLogs() {
    get("https://selenoid.autotests.cloud/status")
    .then()
        .log().all()
        .body("total", is(5));
  }

  @Test
  void checkTotalWithLogs() {
    given()
        .log().all()
        .get("https://selenoid.autotests.cloud/status")
    .then()
        .log().all()
        .body("total", is(5));
  }

  @Test
  void checkTotalWithSomeLogs() {
    given()
        .log().uri()
        .get("https://selenoid.autotests.cloud/status")
    .then()
        .log().body()
        .body("total", is(5));
  }

  @Test
  void checkTotalWithStatusLogs() {
    given()
        .log().uri()
    .when()
        .get("https://selenoid.autotests.cloud/status")
    .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .body("total", is(5))
        .body("browsers.chrome", hasKey("128.0"))
        .body("browsers.firefox", hasKey("125.0"));
  }
}
