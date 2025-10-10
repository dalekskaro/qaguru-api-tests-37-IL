package tests;

import com.github.javafaker.Faker;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import java.time.LocalDate;
import java.util.Locale;
import model.lombok.UserReqresRequestModel;
import model.lombok.UserReqresResponseModel;
import org.assertj.core.api.SoftAssertions;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static specs.ReqresSpec.reqresRequestSpecification;

@Tag("homework-14")
public class HwReqresTests {

  static Faker faker = new Faker(new Locale("en"));

  long userId = faker.number().randomNumber();
  String userEmail = faker.internet().emailAddress(),
      userFirstName = faker.name().firstName(),
      userLastName = faker.name().lastName(),
      date = LocalDate.now().toString();

  @Test
  @DisplayName("PATCH Изменение частичной информации о юзере")
  void patchUserTest() {
    UserReqresRequestModel body = new UserReqresRequestModel();
    body.setEmail(userEmail);

    UserReqresResponseModel response = step("Совершаем вызов метода", () ->
        given(reqresRequestSpecification)
            .body(body)
            .when()
            .patch("/api/users/" + userId)
            .then()
            .statusCode(200)
            .extract().as(UserReqresResponseModel.class));

    step("Проверяем тело ответа", () ->
        {
          SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getEmail())
                .describedAs("Проверяем значение email")
                .isEqualTo(userEmail);

            softAssertions.assertThat(response.getUpdatedAt())
                .describedAs("Проверяем, что значение updatedAt содержит текущую дату")
                .contains(date);
          });
        }
    );
  }

  @Test
  @DisplayName("PUT Изменение информации о юзере")
  void putUserTest() {
    UserReqresRequestModel body = new UserReqresRequestModel();
    body.setEmail(userEmail);
    body.setFirstName(userFirstName);
    body.setLastName(userLastName);

    UserReqresResponseModel response = step("Совершаем вызов метода", () ->
        given(reqresRequestSpecification)
            .body(body)
            .when()
            .put("/api/users/" + userId)
            .then()
            .statusCode(200)
            .extract().as(UserReqresResponseModel.class));

    step("Проверяем тело ответа", () ->
        {
          SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getEmail())
                .describedAs("Проверяем значение email")
                .isEqualTo(userEmail);

            softAssertions.assertThat(response.getFirstName())
                .describedAs("Проверяем значение first_name")
                .isEqualTo(userFirstName);

            softAssertions.assertThat(response.getLastName())
                .describedAs("Проверяем значение last_name")
                .isEqualTo(userLastName);

            softAssertions.assertThat(response.getUpdatedAt())
                .describedAs("Проверяем, что значение updatedAt содержит текущую дату")
                .contains(date);
          });
        }
    );
  }

  @Test
  @DisplayName("DELETE Удаление пользователя")
  void deleteUserTest() {

    int statusCode = step("Совершаем вызов метода", () ->
        given(reqresRequestSpecification)
            .when()
            .delete("/api/users/" + userId)
            .then()
            .extract().statusCode());

    step("Проверяем статус код", () -> assertEquals(204, statusCode));
  }
}
