package uz.keysoft.camunda.spring.boot.starter.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uz.keysoft.camunda.spring.boot.starter.enums.CamundaType;

import java.io.IOException;

public class CamundaTypeDeserializer extends StdDeserializer<CamundaType> {

  public CamundaTypeDeserializer() {
    super(CamundaType.class);
  }

  @Override
  public CamundaType deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return CamundaType.of(p.getValueAsString());
  }
}
