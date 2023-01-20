package uz.keysoft.camunda.spring.boot.starter.service;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uz.keysoft.camunda.spring.boot.starter.dto.incident.IncidentTask;
import uz.keysoft.camunda.spring.boot.starter.dto.incident.Retry;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CamundaIncidentService implements IncidentService {

  RestTemplate restTemplate;

  @Override
  public List<IncidentTask> getIncidentListByProcessId(String processInstanceId) {
    final ResponseEntity<List<IncidentTask>> response = restTemplate.exchange(
      "/incident",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<>() {},
      Map.of("processInstanceId", processInstanceId));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  @Override
  public void retry(String taskId) {
    final Retry retry = Retry.builder().id(taskId).retries(1).build();
    final HttpEntity<Retry> entity = new HttpEntity<>(retry);
    restTemplate.exchange(
      "/{id}/retries",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {},
      Map.of("id", taskId));
  }

}
