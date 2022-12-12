package uz.keysoft.camunda.spring.boot.starter.serializers;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.springframework.boot.jackson.JsonComponent;
import uz.keysoft.camunda.spring.boot.starter.enums.DelegationState;

import java.io.IOException;

@JsonComponent
public class DelegationStateDeserializer extends StdDeserializer<DelegationState> {

  public DelegationStateDeserializer() {
    super(DelegationState.class);
  }

  @Override
  public DelegationState deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
    return DelegationState.valueOf(p.getValueAsString());
  }
}
