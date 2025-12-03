package specs;

import io.restassured.builder.ResponseSpecBuilder;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import io.restassured.specification.ResponseSpecification;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;

public class ResponceSpec {

  public static ResponseSpecification responseSpecification(int code) {
    return new ResponseSpecBuilder()
        .log(STATUS)
        .log(BODY)
        .build()
        .statusCode(code);
  }

  public static ResponseSpecification responseSpecificationWithTwoCode(int firstCode, int secondCode) {
    return new ResponseSpecBuilder()
        .log(STATUS)
        .log(BODY)
        .build()
        .statusCode(anyOf(is(firstCode), is(secondCode)));
  }
}
