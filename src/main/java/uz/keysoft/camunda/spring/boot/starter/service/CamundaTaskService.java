package uz.keysoft.camunda.spring.boot.starter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uz.keysoft.camunda.spring.boot.starter.dto.VariablePair;
import uz.keysoft.camunda.spring.boot.starter.dto.incident.Pagination;
import uz.keysoft.camunda.spring.boot.starter.dto.task.CamundaTask;
import uz.keysoft.camunda.spring.boot.starter.dto.task.CompleteTaskRequest;
import uz.keysoft.camunda.spring.boot.starter.dto.task.User;
import uz.keysoft.camunda.spring.boot.starter.utils.HttpUtils;
import uz.keysoft.camunda.spring.boot.starter.utils.PayloadUtil;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * This class used for working with Camunda tasks by REST API
 * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/task/'>Task</a>
 * @author Eldar Sagitov
 * @since 17.11.2022
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CamundaTaskService implements TaskService {

  RestTemplate restTemplate;
  ObjectMapper mapper;

  /**
   * Get list of tasks by process instance id
   * @param processInstanceId process instance ID
   * @return task list
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/task/get-query/'>Get Tasks</a>
   */
  @Override
  public List<CamundaTask> getTaskListByProcessId(String processInstanceId) {
    final ResponseEntity<List<CamundaTask>> response = restTemplate.exchange(
      "/task?processInstanceId={id}",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<>() {},
      Map.of("id", processInstanceId));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  @Override
  public List<CamundaTask> getAllTaskList(Pagination pagination) {
    final ResponseEntity<List<CamundaTask>> response = restTemplate.exchange(
      "/task?firstResult={page}&maxResults={count}",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<>() {},
      Map.of("page", pagination.getFirstResult(), "count", pagination.getMaxResults()));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  /**
   * Get task by id
   * @param taskId task id
   * @return current task
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/task/get/'>Get Single Task</a>
   */
  @Override
  public CamundaTask getTaskById(String taskId) {
    final ResponseEntity<CamundaTask> response = restTemplate.exchange(
      "/task/{id}",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<>() {},
      Map.of("id", taskId));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  /**
   * Claim current task
   * @param taskId task id which need claim
   * @param userId user id or login
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/task/post-claim/'>Claim Task</a>
   */
  @Override
  public void claim(String taskId, String userId) {
    final User user = User.builder().userId(userId).build();
    final HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    final HttpEntity<User> entity = new HttpEntity<>(user, headers);
    restTemplate.exchange(
      "/task/{id}/claim",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {},
      Map.of("id", taskId));
  }

  /**
   * Unclaim current task
   * @param taskId task id which need unclaim
   * @param userId user id or login
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/task/post-unclaim/'>Unclaim Task</a>
   */
  @Override
  public void unClaim(String taskId, String userId) {
    final User user = User.builder().userId(userId).build();
    final HttpEntity<User> entity = new HttpEntity<>(user, HttpUtils.getHeaders());
    restTemplate.exchange(
      "/task/{id}/unclaim",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {},
      Map.of("id", taskId));
  }

  /**
   * Complete current task
   * @param taskId task id which need to complete
   * @param data complete variables
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/task/post-complete/'>Complete Task</a>
   */
  @Override
  public <T> void complete(String taskId, T data) {
    final CompleteTaskRequest request = CompleteTaskRequest.builder()
      .variables(PayloadUtil.extractPayload(mapper.convertValue(data, Map.class)))
      .build();
    final HttpEntity<CompleteTaskRequest> entity = new HttpEntity<>(request, HttpUtils.getHeaders());
    restTemplate.exchange(
      "/task/{id}/complete",
      HttpMethod.POST,
      entity,
      new ParameterizedTypeReference<>() {},
      Map.of("id", taskId)
    );
  }

  /**
   * Get user task form variables
   * @param taskId task id which variables need watch
   * @return form variables
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/task/get-form-variables/'>Get Task Form Variables</a>
   */
  @Override
  public Map<String, VariablePair> getFormVariables(String taskId) {
    return restTemplate.exchange(
      "/task/{id}/form-variables",
      HttpMethod.GET,
      null,
      new ParameterizedTypeReference<Map<String, VariablePair>>() {},
      Map.of("id", taskId)
    ).getBody();
  }
}
