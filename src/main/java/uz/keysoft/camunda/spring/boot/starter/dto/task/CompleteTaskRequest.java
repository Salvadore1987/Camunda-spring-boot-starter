package uz.keysoft.camunda.spring.boot.starter.dto.task;

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
public class CompleteTaskRequest {
  /**
   * A JSON object containing variable key-value pairs.
   */
  Map<String, VariablePair> variables;
}
