package uz.keysoft.camunda.spring.boot.starter.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import uz.keysoft.camunda.spring.boot.starter.dto.VariablePair;
import uz.keysoft.camunda.spring.boot.starter.enums.CamundaType;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PayloadUtil {

  public static Map<String, VariablePair> extractPayload(Map<String, Object> values) {
    final Map<String, VariablePair> variables = new HashMap<>();
    values.forEach((key, value) -> {
      final VariablePair pair = VariablePair.builder()
        .value(value)
        .type(getType(value.getClass().getName()))
        .build();
      variables.put(key, pair);
    });
    return variables;
  }

  public static CamundaType getType(final String className) {
    if (className.equals(String.class.getName())) {
      return CamundaType.STRING;
    } else if (className.equals(Integer.class.getName())) {
      return CamundaType.INTEGER;
    } else if (className.equals(Long.class.getName())) {
      return CamundaType.LONG;
    } else if (className.equals(Array.class.getName())) {
      return CamundaType.ARRAY;
    } else if (className.equals(Boolean.class.getName())) {
      return CamundaType.BOOLEAN;
    } else if (className.equals(LocalDate.class)
      || className.equals(LocalDateTime.class)) {
      return CamundaType.STRING;
    }
    return CamundaType.OBJECT;
  }

}
