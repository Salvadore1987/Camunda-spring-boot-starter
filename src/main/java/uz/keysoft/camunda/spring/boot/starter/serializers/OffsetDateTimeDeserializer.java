package uz.keysoft.camunda.spring.boot.starter.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;

public class OffsetDateTimeDeserializer extends StdDeserializer<OffsetDateTime> {

  private static final String PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSSZ";

  public OffsetDateTimeDeserializer() {
    super(OffsetDateTime.class);
  }

  @Override
  public OffsetDateTime deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return OffsetDateTime.parse(p.getValueAsString(), DateTimeFormatter.ofPattern(PATTERN));
  }
}
