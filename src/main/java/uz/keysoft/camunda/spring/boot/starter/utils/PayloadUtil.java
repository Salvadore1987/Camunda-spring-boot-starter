package uz.keysoft.camunda.spring.boot.starter.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import uz.keysoft.camunda.spring.boot.starter.dto.ValueInfo;
import uz.keysoft.camunda.spring.boot.starter.dto.VariablePair;
import uz.keysoft.camunda.spring.boot.starter.dto.message.CorrelationKey;
import uz.keysoft.camunda.spring.boot.starter.enums.CamundaType;
import uz.keysoft.camunda.spring.boot.starter.enums.SerializationDataFormat;

import java.lang.reflect.Array;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PayloadUtil {

  public static Map<String, CorrelationKey> extractCorrelationKeys(Map<String, Object> values) {
    Map<String, CorrelationKey> keys = new HashMap<>();
    values.forEach((k, v) -> keys.put(k, CorrelationKey.builder()
      .type(getType(v.getClass().getName()))
      .value(v)
      .build()));
    return keys;
  }

  public static <T> Map<String, VariablePair> extractToJsonPayload(T data, ObjectMapper mapper) {
    final VariablePair variable = VariablePair.builder()
      .type(CamundaType.STRING)
      .value(convertToJson(data, mapper))
      .valueInfo(ValueInfo.builder()
        .serializationDataFormat(SerializationDataFormat.JSON)
        .objectTypeName(data.getClass().getName())
        .build())
      .build();
    final String key = StringUtils.uncapitalize(data.getClass().getSimpleName());
    return Map.of(key, variable);
  }

  public static Map<String, VariablePair> extractPayload(Map<String, Object> values) {
    final Map<String, VariablePair> variables = new HashMap<>();
    values.forEach((key, value) -> {
      final VariablePair pair = VariablePair.builder()
        .value(value)
        .type(getType(value.getClass().getName()))
        .valueInfo(getType(value.getClass().getName()).equals(CamundaType.OBJECT) ? ValueInfo.builder()
          .objectTypeName(value.getClass().getName())
          .serializationDataFormat(SerializationDataFormat.JSON)
          .build() : null)
        .build();
      variables.put(key, pair);
    });
    return variables;
  }

  private static <T> String convertToJson(T data, ObjectMapper mapper) {
    try {
      return mapper.writeValueAsString(data);
    } catch (Exception ex) {
      log.error(ex.getMessage());
      throw new IllegalArgumentException("Can't parse object to string");
    }
  }

  public static CamundaType getType(final String className) {
    if (className.equals(String.class.getName())) {
      return CamundaType.STRING;
    } else if (className.equals(Integer.class.getName())) {
      return CamundaType.INTEGER;
    } else if (className.equals(Long.class.getName())) {
      return CamundaType.LONG;
    } else if (className.equals(Float.class.getName()) || className.equals(Double.class.getName())) {
      return CamundaType.DOUBLE;
    } else if (className.equals(Array.class.getName())) {
      return CamundaType.ARRAY;
    } else if (className.equals(Boolean.class.getName())) {
      return CamundaType.BOOLEAN;
    } else if (className.equals(LocalDate.class.getName())
      || className.equals(LocalDateTime.class.getName())) {
      return CamundaType.STRING;
    }
    return CamundaType.OBJECT;
  }

}
