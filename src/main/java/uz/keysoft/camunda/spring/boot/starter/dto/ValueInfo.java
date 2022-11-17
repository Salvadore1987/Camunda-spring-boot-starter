package uz.keysoft.camunda.spring.boot.starter.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.keysoft.camunda.spring.boot.starter.enums.SerializationDataFormat;
import uz.keysoft.camunda.spring.boot.starter.serializers.DataFormatDeserializer;
import uz.keysoft.camunda.spring.boot.starter.serializers.DataFormatSerializer;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ValueInfo {

  String objectTypeName;
  @JsonSerialize(using = DataFormatSerializer.class)
  @JsonDeserialize(using = DataFormatDeserializer.class)
  SerializationDataFormat serializationDataFormat;

}
