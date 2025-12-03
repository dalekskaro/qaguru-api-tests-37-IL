package specs;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import io.restassured.specification.RequestSpecification;

public class DemoQaSpec {

  public static RequestSpecification demoQaRequestSpecification = with()
      .filter(withCustomTemplates())
      .log().uri()
      .log().body()
      .log().headers()
      .contentType(JSON)
      .baseUri("https://demoqa.com");
}
