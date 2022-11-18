package uz.keysoft.camunda.spring.boot.starter.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import uz.keysoft.camunda.spring.boot.starter.dto.process.StartProcessRequest;
import uz.keysoft.camunda.spring.boot.starter.dto.process.StartProcessResult;
import uz.keysoft.camunda.spring.boot.starter.utils.PayloadUtil;

import java.util.Map;
import java.util.Optional;

/**
 * This class used for interact with Camunda Process Management System by REST API
 * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/process-definition/'>Process Definition</a>
 * @author Sagitov Eldar
 * @since 17.11.2022
 */
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CamundaProcessService implements ProcessService {

  RestTemplate restTemplate;
  ObjectMapper mapper;

  /**
   * Start camunda process by Definition ID with json string variable
   * @param id definition ID
   * @param businessKey process business key
   * @param data data to be used in process
   * @return returned by Camunda data
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/process-definition/post-start-process-instance/'>Start Instance</a>
   */
  @Override
  public <T> StartProcessResult startProcessById(String id, String businessKey, T data) {
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/{id}/start",
      HttpMethod.POST,
      httpEntity(businessKey, data),
      StartProcessResult.class,
      Map.of("id", id));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  /**
   * Start camunda process by definition key with json string variable
   * @param key definition key
   * @param businessKey process business key
   * @param data data to be used in process
   * @return returned by Camunda data
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/process-definition/post-start-process-instance/'>Start Instance</a>
   */
  @Override
  public <T> StartProcessResult startProcessByKey(String key, String businessKey, T data) {
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/key/{key}/start",
      HttpMethod.POST,
      httpEntity(businessKey, data),
      StartProcessResult.class,
      Map.of("key", key));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  /**
   * Start camunda process by tenant ID with json string variable
   * @param key definition key
   * @param tenantId tenant ID
   * @param businessKey process business key
   * @param data data to be used in process
   * @return returned by Camunda data
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/process-definition/post-start-process-instance/'>Start Instance</a>
   */
  @Override
  public <T> StartProcessResult startProcessByTenantId(String key, String tenantId, String businessKey, T data) {
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/key/{key}/tenant-id/{tenant-id}/start",
      HttpMethod.POST,
      httpEntity(businessKey, data),
      StartProcessResult.class,
      Map.of("key", key, "tenant-id", tenantId));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  /**
   * Start camunda process by Definition ID with map Variables
   * @param id definition ID
   * @param businessKey process business key
   * @param variables data to be used in process in Map.class format
   * @return returned by Camunda data
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/process-definition/post-start-process-instance/'>Start Instance</a>
   */
  @Override
  public StartProcessResult startProcessById(String id, String businessKey, Map<String, Object> variables) {
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/{id}/start",
      HttpMethod.POST,
      httpEntity(businessKey, variables),
      StartProcessResult.class,
      Map.of("id", id));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  /**
   * Start camunda process by definition key with map Variables
   * @param key definition key
   * @param businessKey process business key
   * @param variables data to be used in process in Map.class format
   * @return returned by Camunda data
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/process-definition/post-start-process-instance/'>Start Instance</a>
   */
  @Override
  public StartProcessResult startProcessByKey(String key, String businessKey, Map<String, Object> variables) {
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/key/{key}/start",
      HttpMethod.POST,
      httpEntity(businessKey, variables),
      StartProcessResult.class,
      Map.of("key", key));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  /**
   * Start camunda process by tenant ID with map Variables
   * @param key definition key
   * @param tenantId tenant ID
   * @param businessKey process business key
   * @param variables data to be used in process in Map.class format
   * @return returned by Camunda data
   * @see <a href='https://docs.camunda.org/manual/7.5/reference/rest/process-definition/post-start-process-instance/'>Start Instance</a>
   */
  @Override
  public StartProcessResult startProcessByTenantId(String key, String tenantId, String businessKey, Map<String, Object> variables) {
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/key/{key}/tenant-id/{tenant-id}/start",
      HttpMethod.POST,
      httpEntity(businessKey, variables),
      StartProcessResult.class,
      Map.of("key", key, "tenant-id", tenantId));
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  private <T> StartProcessRequest buildRequest(String businessKey, T data) {
    return StartProcessRequest.builder()
      .businessKey(businessKey)
      .variables(PayloadUtil.extractToJsonPayload(data, mapper))
      .build();
  }

  private StartProcessRequest buildRequest(String businessKey, Map<String, Object> variables) {
    return StartProcessRequest.builder()
      .businessKey(businessKey)
      .variables(PayloadUtil.extractPayload(variables))
      .build();
  }

  private <T> HttpEntity<StartProcessRequest> httpEntity(String businessKey, T data) {
    return new HttpEntity<>(buildRequest(businessKey, data));
  }

  private HttpEntity<StartProcessRequest> httpEntity(String businessKey, Map<String, Object> variables) {
    return new HttpEntity<>(buildRequest(businessKey, variables));
  }

}
