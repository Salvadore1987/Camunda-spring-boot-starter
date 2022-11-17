package uz.keysoft.camunda.spring.boot.starter.dto.process;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.keysoft.camunda.spring.boot.starter.dto.VariablePair;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartProcessRequest {

  /**
   * A JSON object containing the variables the process is to be initialized with.
   * Each key corresponds to a variable name and each value to a variable value.
   * A variable value is a JSON object with the following properties:
   */
  Map<String, VariablePair> variables;
  /**
   * The business key the process instance is to be initialized with.
   * The business key uniquely identifies the process instance in the context of the given process definition.
   */
  String businessKey;

}
