package uz.keysoft.camunda.spring.boot.starter.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uz.keysoft.camunda.spring.boot.starter.enums.CamundaType;

import java.io.IOException;

public class CamundaTypeSerializer extends StdSerializer<CamundaType> {

  public CamundaTypeSerializer() {
    super(CamundaType.class);
  }

  @Override
  public void serialize(CamundaType value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(value.getName());
  }
}
