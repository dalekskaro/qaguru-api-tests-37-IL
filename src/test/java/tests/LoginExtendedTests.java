package tests;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.qameta.allure.Allure.step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import model.lombok.LoginBodyLombokModel;
import model.lombok.LoginResponseLombokModel;
import model.lombok.MissingPasswordModel;
import model.pojo.LoginBodyModel;
import model.pojo.LoginResponseModel;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static specs.LoginSpec.loginRequestSpec;
import static specs.LoginSpec.loginResponseSpec;
import static specs.LoginSpec.missingPasswordResponseSpec;

public class LoginExtendedTests {

  @BeforeAll
  public static void setUp() {
    RestAssured.baseURI = "https://reqres.in";
  }

  @Test
  void pojoSuccessfulLoginTest() {
    LoginBodyModel authData = new LoginBodyModel();
    authData.setEmail("eve.holt@reqres.in");
    authData.setPassword("cityslicka");

    LoginResponseModel response = given()
        .header("x-api-key", "reqres-free-v1")
        .body(authData)
        .contentType(JSON)
        .log().uri()
        .log().body()
        .log().headers()
        .when()
        .post("/api/login")
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .extract().as(LoginResponseModel.class);

    assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
  }

  @Test
  void lombokSuccessfulLoginTest() {
    LoginBodyLombokModel authData = new LoginBodyLombokModel();
    authData.setEmail("eve.holt@reqres.in");
    authData.setPassword("cityslicka");

    LoginResponseLombokModel response = given()
        .header("x-api-key", "reqres-free-v1")
        .body(authData)
        .contentType(JSON)
        .log().uri()
        .log().body()
        .log().headers()
        .when()
        .post("/api/login")
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .extract().as(LoginResponseLombokModel.class);

    assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
  }

  @Test
  void allureSuccessfulLoginTest() {
    LoginBodyLombokModel authData = new LoginBodyLombokModel();
    authData.setEmail("eve.holt@reqres.in");
    authData.setPassword("cityslicka");

    LoginResponseLombokModel response = given()
        .filter(new AllureRestAssured())
        .header("x-api-key", "reqres-free-v1")
        .body(authData)
        .contentType(JSON)
        .log().uri()
        .log().body()
        .log().headers()
        .when()
        .post("/api/login")
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .extract().as(LoginResponseLombokModel.class);

    assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
  }

  @Test
  void customAllureSuccessfulLoginTest() {
    LoginBodyLombokModel authData = new LoginBodyLombokModel();
    authData.setEmail("eve.holt@reqres.in");
    authData.setPassword("cityslicka");

    LoginResponseLombokModel response = given()
        .filter(withCustomTemplates())
        .header("x-api-key", "reqres-free-v1")
        .body(authData)
        .contentType(JSON)
        .log().uri()
        .log().body()
        .log().headers()
        .when()
        .post("/api/login")
        .then()
        .log().status()
        .log().body()
        .statusCode(200)
        .extract().as(LoginResponseLombokModel.class);

    assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
  }

  @Test
  void stepsSuccessfulLoginTest() {
    LoginBodyLombokModel authData = new LoginBodyLombokModel();
    authData.setEmail("eve.holt@reqres.in");
    authData.setPassword("cityslicka");

    LoginResponseLombokModel response = step("Make request", () ->
        given()
            .filter(withCustomTemplates())
            .header("x-api-key", "reqres-free-v1")
            .body(authData)
            .contentType(JSON)
            .log().uri()
            .log().body()
            .log().headers()
            .when()
            .post("/api/login")
            .then()
            .log().status()
            .log().body()
            .statusCode(200)
            .extract().as(LoginResponseLombokModel.class)
    );

    step("Check response", () ->
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
  }

  @Test
  void specsSuccessfulLoginTest() {
    LoginBodyLombokModel authData = new LoginBodyLombokModel();
    authData.setEmail("eve.holt@reqres.in");
    authData.setPassword("cityslicka");

    LoginResponseLombokModel response = step("Make request", () ->
        given(loginRequestSpec)
            .filter(withCustomTemplates())
            .body(authData)
            .when()
            .post()
            .then()
            .spec(loginResponseSpec)
            .extract().as(LoginResponseLombokModel.class)
    );

    step("Check response", () ->
        assertEquals("QpwL5tke4Pnpja7X4", response.getToken()));
  }

  @Test
  void missingPasswordTest() {
    LoginBodyLombokModel authData = new LoginBodyLombokModel();
    authData.setEmail("eve.holt@reqres.in");

    MissingPasswordModel response = step("Make request", ()->
        given(loginRequestSpec)
            .body(authData)

            .when()
            .post()

            .then()
            .spec(missingPasswordResponseSpec)
            .extract().as(MissingPasswordModel.class));

    step("Check response", ()->
        assertEquals("Missing password", response.getError()));
  }

}
