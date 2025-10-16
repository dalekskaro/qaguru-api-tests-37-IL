package tests;

import static io.qameta.allure.Allure.step;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import static io.restassured.RestAssured.given;
import model.BookDemoQaModel;
import model.BooksDemoQaModel;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import static specs.DemoQaSpec.demoQaRequestSpecification;
import static specs.ResponceSpec.responseSpecification;

@Tag("homework-14")
@Epic("DemoQa. Проверка данных по книге")
@Owner("Irina Attano")
public class HwBooksTests {

  @Test
  @Description("Получение списка книг")
  @DisplayName("GET /BookStore/v1/Books")
  void getAllBooksTest() {
    BooksDemoQaModel response = step("Совершаем вызов метода", () ->
        given(demoQaRequestSpecification)
            .when()
            .get("/BookStore/v1/Books")
            .then()
            .spec(responseSpecification(200))
            .extract().as(BooksDemoQaModel.class));

    step("Проверяем тело ответа", () ->
        {
          BookDemoQaModel book = response.getBooks().get(0);

          SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(book.getIsbn())
                .describedAs("Проверяем значение isbn")
                .isEqualTo("9781449325862");

            softAssertions.assertThat(book.getTitle())
                .describedAs("Проверяем значение title")
                .isEqualTo("Git Pocket Guide");

            softAssertions.assertThat(book.getAuthor())
                .describedAs("Проверяем значение author")
                .isEqualTo("Richard E. Silverman");

            softAssertions.assertThat(book.getPages())
                .describedAs("Проверяем значение pages")
                .isEqualTo(234);

            softAssertions.assertThat(book.getDescription())
                .describedAs("Проверяем значение description")
                .contains("This pocket guide");

            softAssertions.assertThat(book.getWebsite())
                .describedAs("Проверяем значение website")
                .contains("chimera.labs.oreilly");
          });
        }
    );
  }

  @Test
  @Description("Получение списка книг при помощи ISBN")
  @DisplayName("GET /BookStore/v1/Books?ISBN={isbn}")
  void getBooksByIsbnTest() {
    BooksDemoQaModel response = step("Совершаем вызов метода", () ->
        given(demoQaRequestSpecification)
            .queryParam("ISBN", "9781449325862")
            .when()
            .get("/BookStore/v1/Books")
            .then()
            .spec(responseSpecification(200))
            .extract().as(BooksDemoQaModel.class));

    step("Проверяем тело ответа", () ->
        {
          BookDemoQaModel book = response.getBooks().get(0);

          SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(book.getIsbn())
                .describedAs("Проверяем значение isbn")
                .isEqualTo("9781449325862");

            softAssertions.assertThat(book.getTitle())
                .describedAs("Проверяем значение title")
                .isEqualTo("Git Pocket Guide");

            softAssertions.assertThat(book.getAuthor())
                .describedAs("Проверяем значение author")
                .isEqualTo("Richard E. Silverman");

            softAssertions.assertThat(book.getPages())
                .describedAs("Проверяем значение pages")
                .isEqualTo(234);

            softAssertions.assertThat(book.getDescription())
                .describedAs("Проверяем значение description")
                .contains("This pocket guide");

            softAssertions.assertThat(book.getWebsite())
                .describedAs("Проверяем значение website")
                .contains("chimera.labs.oreilly");
          });
        }
    );
  }
}
