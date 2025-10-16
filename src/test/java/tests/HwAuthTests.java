package tests;

import com.github.javafaker.Faker;
import static io.qameta.allure.Allure.step;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import static io.restassured.RestAssured.given;
import java.time.Year;
import java.util.Locale;
import model.AuthDemoQaModel;
import model.GenerateTokenDemoQaModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static specs.DemoQaSpec.demoQaRequestSpecification;
import static specs.ResponceSpec.responseSpecification;
import static specs.ResponceSpec.responseSpecificationWithTwoCode;

@Tag("homework-14")
@Epic("DemoQa. Проверка авторизации")
@Owner("Irina Attano")
public class HwAuthTests {

  static Faker faker = new Faker(new Locale("en"));

  static String loginForUser = faker.overwatch().hero(),
      passwordForUser = "1Qa!2Qa!3Qa!";

  @BeforeAll
  static void auth() {
    AuthDemoQaModel body = new AuthDemoQaModel();
    body.setUserName(loginForUser);
    body.setPassword(passwordForUser);

    int status = step("Совершаем вызов метода добавления пользователя", () ->
        given(demoQaRequestSpecification)
            .body(body)
            .when()
            .post("/Account/v1/User")
            .then()
            .spec(responseSpecificationWithTwoCode(201,406))
            .extract().statusCode());

    step("Проверяем, добавлен ли пользователь или он уже существует", () ->
    {
      if (status == 201) {
        System.out.println("Пользователь добавлен");
      } else {
        if (status == 406) {
          System.out.println("Пользователь НЕ добавлен - уже существует");
        }
      }
    });
  }

  @Test
  @Description("Успешная авторизация (генерация токена)")
  @DisplayName("POST /Account/v1/GenerateToken")
  void successfulLoginTest() {
    String year = String.valueOf(Year.now().getValue());
    AuthDemoQaModel body = new AuthDemoQaModel();
    body.setUserName(loginForUser);
    body.setPassword(passwordForUser);

    GenerateTokenDemoQaModel response = step("Совершаем вызов метода", () ->
        given(demoQaRequestSpecification)
            .body(body)
            .when()
            .post("/Account/v1/GenerateToken")
            .then()
            .spec(responseSpecification(200))
            .extract().as(GenerateTokenDemoQaModel.class));

    step("Проверяем тело ответа", () ->
        {
          SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getToken())
                .describedAs("Проверяем, что значение token не пусто")
                .isNotNull();

            softAssertions.assertThat(response.getStatus())
                .describedAs("Проверяем значение status")
                .isEqualTo("Success");

            softAssertions.assertThat(response.getResult())
                .describedAs("Проверяем значение result")
                .isEqualTo("User authorized successfully.");

            softAssertions.assertThat(response.getExpires())
                .describedAs("Проверяем, что значение expires содержит текущий год")
                .contains(year);
          });
        }
    );
  }

  @Test
  @Description("Неуспешная авторизация (пароль не верный)")
  @DisplayName("POST /Account/v1/GenerateToken. Wrong password")
  void unsuccessfulLoginIncorrectPasswordTest() {
    AuthDemoQaModel body = new AuthDemoQaModel();
    body.setUserName(loginForUser);
    body.setPassword(passwordForUser + "1");

    GenerateTokenDemoQaModel response = step("Совершаем вызов метода", () ->
        given(demoQaRequestSpecification)
            .body(body)
            .when()
            .post("/Account/v1/GenerateToken")
            .then()
            .spec(responseSpecification(200))
            .extract().as(GenerateTokenDemoQaModel.class));

    step("Проверяем тело ответа", () ->
        {
          SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getToken())
                .describedAs("Проверяем, что значение token пусто")
                .isNull();

            softAssertions.assertThat(response.getStatus())
                .describedAs("Проверяем значение status")
                .isEqualTo("Failed");

            softAssertions.assertThat(response.getResult())
                .describedAs("Проверяем значение result")
                .isEqualTo("User authorization failed.");

            softAssertions.assertThat(response.getExpires())
                .describedAs("Проверяем, что значение expires пусто")
                .isNull();
          });
        }
    );
  }

  @Test
  @Description("Неуспешная авторизация (пользователя не существует)")
  @DisplayName("POST /Account/v1/GenerateToken. User not exist")
  void unsuccessfulLoginIncorrectUserNameTest() {
    AuthDemoQaModel body = new AuthDemoQaModel();
    body.setUserName(loginForUser + "1");
    body.setPassword(passwordForUser);

    GenerateTokenDemoQaModel response = step("Совершаем вызов метода", () ->
        given(demoQaRequestSpecification)
            .body(body)
            .when()
            .post("/Account/v1/GenerateToken")
            .then()
            .spec(responseSpecification(200))
            .extract().as(GenerateTokenDemoQaModel.class));

    step("Проверяем тело ответа", () ->
        {
          SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getToken())
                .describedAs("Проверяем, что значение token пусто")
                .isNull();

            softAssertions.assertThat(response.getStatus())
                .describedAs("Проверяем значение status")
                .isEqualTo("Failed");

            softAssertions.assertThat(response.getResult())
                .describedAs("Проверяем значение result")
                .isEqualTo("User authorization failed.");

            softAssertions.assertThat(response.getExpires())
                .describedAs("Проверяем, что значение expires пусто")
                .isNull();
          });
        }
    );
  }
}
