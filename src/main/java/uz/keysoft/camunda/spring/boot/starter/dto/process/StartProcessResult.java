package uz.keysoft.camunda.spring.boot.starter.dto.process;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.keysoft.camunda.spring.boot.starter.dto.Link;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class StartProcessResult {

  /**
   * The id of the process instance.
   */
  String id;

  /**
   * The id of the process definition.
   */
  String definitionId;

  /**
   * The business key of the process instance.
   */
  String businessKey;

  /**
   * The case instance id of the process instance.
   */
  String caseInstanceId;

  /**
   * The tenant id of the process instance.
   */
  String tenantId;

  /**
   * A flag indicating whether the instance is still running or not.
   */
  Boolean ended;

  /**
   * A flag indicating whether the instance is suspended or not.
   */
  Boolean suspended;

  /**
   * A JSON array containing links to interact with the instance.
   */
  List<Link> links;

}
