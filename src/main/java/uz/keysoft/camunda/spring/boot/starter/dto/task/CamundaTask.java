package uz.keysoft.camunda.spring.boot.starter.dto.task;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.keysoft.camunda.spring.boot.starter.serializers.OffsetDateTimeDeserializer;
import uz.keysoft.camunda.spring.boot.starter.enums.DelegationState;
import uz.keysoft.camunda.spring.boot.starter.serializers.DelegationStateDeserializer;

import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CamundaTask {

  /**
   * The task id.
   */
  String id;
  /**
   * The task name.
   */
  String name;
  /**
   * The assignee's id.
   */
  String assignee;
  /**
   * The owner's id.
   */
  String owner;
  /**
   * The date the task was created on. Has the format yyyy-MM-dd'T'HH:mm:ss.
   */
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  OffsetDateTime created;
  /**
   * The task's due date. Has the format yyyy-MM-dd'T'HH:mm:ss.
   */
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  OffsetDateTime due;
  /**
   * The follow-up date for the task. Format yyyy-MM-dd'T'HH:mm:ss.
   */
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  OffsetDateTime followUp;
  /**
   * The task's delegation state. Possible values are PENDING and RESOLVED.
   */
  @JsonDeserialize(using = DelegationStateDeserializer.class)
  DelegationState delegationState;
  /**
   * The task's description.
   */
  String description;
  /**
   * The id of the execution the task belongs to.
   */
  String executionId;
  /**
   * The id the parent task, if this task is a subtask.
   */
  String parentTaskId;
  /**
   * The task's priority.
   */
  Number priority;
  /**
   * The id of the process definition the task belongs to.
   */
  String processDefinitionId;
  /**
   * The id of the process instance the task belongs to.
   */
  String processInstanceId;
  /**
   * The id of the case execution the task belongs to.
   */
  String caseExecutionId;
  /**
   * The id of the case definition the task belongs to.
   */
  String caseDefinitionId;
  /**
   * The id of the case instance the task belongs to.
   */
  String caseInstanceId;
  /**
   * The task's key.
   */
  String taskDefinitionKey;
  /**
   * If not null, the form key for the task.
   */
  String formKey;
  /**
   * If not null, the tenant id of the task.
   */
  String tenantId;

}
