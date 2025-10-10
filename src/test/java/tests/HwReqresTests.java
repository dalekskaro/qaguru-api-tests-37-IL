package tests;

import com.github.javafaker.Faker;
import static io.qameta.allure.Allure.step;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Story;
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
@Epic("Reqres. Проверка изменения данных юзера")
@Owner("Irina Attano")
public class HwReqresTests {

  static Faker faker = new Faker(new Locale("en"));

  long userId = faker.number().randomNumber();
  String userEmail = faker.internet().emailAddress(),
      userFirstName = faker.name().firstName(),
      userLastName = faker.name().lastName(),
      date = LocalDate.now().toString();

  @Test
  @Description("Изменение частичной информации о юзере")
  @DisplayName("PATCH /api/users/{id}")
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
  @Description("Изменение информации о юзере")
  @DisplayName("PUT /api/users/{id}")
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
  @Description("Удаление пользователя")
  @DisplayName("DELETE /api/users/{id}")
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
