package uz.keysoft.camunda.spring.boot.starter.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import uz.keysoft.camunda.spring.boot.starter.enums.SerializationDataFormat;

import java.io.IOException;

public class DataFormatSerializer extends StdSerializer<SerializationDataFormat> {

  public DataFormatSerializer() {
    super(SerializationDataFormat.class);
  }

  @Override
  public void serialize(SerializationDataFormat value, JsonGenerator gen, SerializerProvider provider) throws IOException {
    gen.writeString(value.getFormat());
  }
}
