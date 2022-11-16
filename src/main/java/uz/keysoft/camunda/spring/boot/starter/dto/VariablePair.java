package uz.keysoft.camunda.spring.boot.starter.dto;

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
public class VariablePair {

  Object value;
  @JsonSerialize(using = CamundaTypeSerializer.class)
  @JsonDeserialize(using = CamundaTypeDeserializer.class)
  CamundaType type;

}
