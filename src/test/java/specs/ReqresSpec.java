package specs;

import static helpers.CustomAllureListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.http.ContentType.JSON;
import io.restassured.specification.RequestSpecification;

public class ReqresSpec {

  public static RequestSpecification reqresRequestSpecification = with()
      .filter(withCustomTemplates())
      .header("x-api-key", System.getProperty("apiKey"))
      .log().uri()
      .log().body()
      .log().headers()
      .contentType(JSON)
      .baseUri("https://reqres.in");
}
