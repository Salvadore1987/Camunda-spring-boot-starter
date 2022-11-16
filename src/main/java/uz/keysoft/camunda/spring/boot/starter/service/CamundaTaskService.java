package uz.keysoft.camunda.spring.boot.starter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uz.keysoft.camunda.spring.boot.starter.dto.task.CamundaTask;
import uz.keysoft.camunda.spring.boot.starter.utils.PayloadUtil;
import uz.keysoft.camunda.spring.boot.starter.dto.task.CompleteTaskRequest;
import uz.keysoft.camunda.spring.boot.starter.dto.task.User;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CamundaTaskService implements TaskService {

  RestTemplate restTemplate;
  ObjectMapper mapper;

  @Override
  public List<CamundaTask> getTaskListById(String processInstanceId) {
    final Map<String, String> params = new HashMap<>();
    params.put("processInstanceId", processInstanceId);
    final ResponseEntity<List<CamundaTask>> response = restTemplate.exchange(
      "/task",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<>() {},
      params);
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  @Override
  public void claim(String taskId, String userId) {
    final Map<String, String> params = new HashMap<>();
    params.put("id", taskId);
    final User user = User.builder().userId(userId).build();
    final HttpEntity<User> entity = new HttpEntity<>(user);
    restTemplate.exchange(
      "/task/{id}/claim",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {},
      params);
  }

  @Override
  public void unClaim(String taskId, String userId) {
    final Map<String, String> params = new HashMap<>();
    params.put("id", taskId);
    final User user = User.builder().userId(userId).build();
    final HttpEntity<User> entity = new HttpEntity<>(user);
    restTemplate.exchange(
      "/task/{id}/unclaim",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {},
      params);
  }

  @Override
  public <T> void complete(String taskId, T data) {
    final CompleteTaskRequest request = CompleteTaskRequest.builder()
      .variables(PayloadUtil.extractPayload(mapper.convertValue(data, Map.class)))
      .build();
    final HttpEntity<CompleteTaskRequest> entity = new HttpEntity<>(request);
    final Map<String, String> params = new HashMap<>();
    params.put("id", taskId);
    restTemplate.exchange(
      "/task/{id}/complete",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {},
      params
    );
  }
}
