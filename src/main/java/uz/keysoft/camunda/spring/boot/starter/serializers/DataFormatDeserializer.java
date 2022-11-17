package uz.keysoft.camunda.spring.boot.starter.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import uz.keysoft.camunda.spring.boot.starter.enums.SerializationDataFormat;

import java.io.IOException;

public class DataFormatDeserializer extends StdDeserializer<SerializationDataFormat> {

  public DataFormatDeserializer() {
    super(SerializationDataFormat.class);
  }

  @Override
  public SerializationDataFormat deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return SerializationDataFormat.of(p.getValueAsString());
  }
}
