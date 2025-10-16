package model;

import lombok.Data;

@Data
public class GenerateTokenDemoQaModel {

  private String token;
  private String expires;
  private String status;
  private String result;
}
