package uz.keysoft.camunda.spring.boot.starter.enums;

import java.util.Arrays;

public enum CamundaType {

  STRING("String"), LONG("Long"), INTEGER("Integer"), BOOLEAN("Boolean"), ARRAY("Array"), OBJECT("Object");

  private final String name;

  CamundaType(final String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public static CamundaType of(final String type) {
    return Arrays.stream(CamundaType.values()).filter(v -> v.getName().equals(type)).findFirst().orElseThrow();
  }

}
