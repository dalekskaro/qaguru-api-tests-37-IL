package specs;

import io.restassured.builder.ResponseSpecBuilder;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import io.restassured.specification.ResponseSpecification;

public class ResponceSpec {

  public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
      .log(STATUS)
      .log(BODY)
      .build();
}
