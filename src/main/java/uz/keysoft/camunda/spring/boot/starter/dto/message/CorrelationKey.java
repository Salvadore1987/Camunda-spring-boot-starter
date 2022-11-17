package uz.keysoft.camunda.spring.boot.starter.dto.message;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.keysoft.camunda.spring.boot.starter.enums.CamundaType;
import uz.keysoft.camunda.spring.boot.starter.serializers.CamundaTypeDeserializer;
import uz.keysoft.camunda.spring.boot.starter.serializers.CamundaTypeSerializer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CorrelationKey {
  /**
   * The variable's value.
   */
  Object value;
  /**
   * The value type of the variable. Valid types are String, Integer, Short, Long, Double and Date.
   */
  @JsonSerialize(using = CamundaTypeSerializer.class)
  @JsonDeserialize(using = CamundaTypeDeserializer.class)
  CamundaType type;

}
