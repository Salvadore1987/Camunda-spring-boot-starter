package uz.keysoft.camunda.spring.boot.starter.dto.incident;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import uz.keysoft.camunda.spring.boot.starter.serializers.OffsetDateTimeDeserializer;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class IncidentTask {

  String id;
  String processDefinitionId;
  String processInstanceId;
  String executionId;
  @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
  LocalDateTime incidentTimestamp;
  String incidentType;
  String activityId;
  String failedActivityId;
  String causeIncidentId;
  String rootCauseIncidentId;
  String configuration;
  String incidentMessage;
  String tenantId;
  String jobDefinitionId;

}
