package model.lombok;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class UserReqresResponseModel {

  String email;
  @JsonProperty("first_name")
  String firstName;
  @JsonProperty("last_name")
  String lastName;
  String updatedAt;
}
