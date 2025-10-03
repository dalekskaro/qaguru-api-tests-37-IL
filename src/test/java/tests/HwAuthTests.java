package tests;

import com.fasterxml.jackson.databind.ObjectMapper;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import java.io.IOException;
import java.io.InputStream;
import model.AuthRequest;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("homework-13")
public class HwAuthTests extends BaseTest{
  private static final ObjectMapper MAPPER = new ObjectMapper();

  @BeforeAll
  static void auth() throws IOException {
    InputStream is = BaseTest.class.getClassLoader().getResourceAsStream("json/AuthBody.json");
    AuthRequest request = MAPPER.readValue(is, AuthRequest.class);

    int status = given()
        .body(request)
        .contentType(JSON)
        .log().uri()
        .when()
        .post("/Account/v1/User")
        .then()
        .log().status()
        .extract().statusCode();

    if (status == 201) {
      System.out.println("Пользователь добавлен");
    } else {
      if (status == 406) {
        System.out.println("Пользователь НЕ добавлен - уже существует");
      }
    }
  }

  @Test
  @DisplayName("Успешная авторизация (генерация токена)")
  void successfulLoginTest() throws IOException {
    InputStream is = getClass().getClassLoader().getResourceAsStream("json/AuthBody.json");
    AuthRequest request = MAPPER.readValue(is, AuthRequest.class);

    given()
        .body(request)
        .contentType(JSON)
        .log().uri()

        .when()
        .post("/Account/v1/GenerateToken")

        .then()
        .log().all()
        .statusCode(200)
        .body("status", is("Success"))
        .body("result", is("User authorized successfully."));
  }

  @Test
  @DisplayName("Неспешная авторизация (пароль не верный)")
  void unsuccessfulLoginIncorrectPasswordTest() {
    String authData = "{\"userName\": \"attano37\", \"password\": \".String37\"}";

    given()
        .body(authData)
        .contentType(JSON)
        .log().uri()

        .when()
        .post("/Account/v1/GenerateToken")

        .then()
        .log().all()
        .statusCode(200)
        .body("token", is(nullValue()))
        .body("expires", is(nullValue()))
        .body("status", is("Failed"))
        .body("result", is("User authorization failed."));
  }

  @Test
  @DisplayName("Неспешная авторизация (пользователя не существует)")
  void unsuccessfulLoginIncorrectUserNameTest() {
    String authData = "{\"userName\": \"attano\", \"password\": \".String37!\"}";

    given()
        .body(authData)
        .contentType(JSON)
        .log().uri()

        .when()
        .post("/Account/v1/GenerateToken")

        .then()
        .log().all()
        .statusCode(200)
        .body("token", is(nullValue()))
        .body("expires", is(nullValue()))
        .body("status", is("Failed"))
        .body("result", is("User authorization failed."));
  }
}
