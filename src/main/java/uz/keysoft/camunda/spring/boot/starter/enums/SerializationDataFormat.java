package uz.keysoft.camunda.spring.boot.starter.enums;

import java.util.Arrays;

public enum SerializationDataFormat {

  JSON("application/json"),
  XML("application/xml"),
  JAVA("application/x-java-serialized-object");

  private final String format;

  SerializationDataFormat(final String format) {
    this.format = format;
  }

  public String getFormat() {
    return format;
  }

  public static SerializationDataFormat of(final String name) {
    return Arrays.stream(SerializationDataFormat.values()).filter(i -> i.getFormat().equals(name)).findFirst().orElseThrow();
  }

}
