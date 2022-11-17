package uz.keysoft.camunda.spring.boot.starter.dto.message;

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
public class CorrelateMessageRequest {

  /**
   * The name of the message to deliver.
   */
  String messageName;
  /**
   * Used for correlation of process instances that wait for incoming messages.
   * Will only correlate to executions that belong to a process instance with the provided business key.
   */
  String businessKey;
  /**
   * Used for correlation of process instances that wait for incoming messages.
   * Has to be a JSON object containing key-value pairs that are matched against process instance variables during correlation.
   * Each key is a variable name and each value a JSON variable value object with the following properties.
   */
  Map<String, CorrelationKey> correlationKeys;
  /**
   * A map of variables that is injected into the triggered execution or process instance after the message has been delivered.
   * Each key is a variable name and each value a JSON variable value object with the following properties.
   */
  Map<String, VariablePair> processVariables;



}
