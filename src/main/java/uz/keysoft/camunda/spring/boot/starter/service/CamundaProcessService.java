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
import uz.keysoft.camunda.spring.boot.starter.dto.process.StartProcessResult;
import uz.keysoft.camunda.spring.boot.starter.utils.PayloadUtil;
import uz.keysoft.camunda.spring.boot.starter.dto.process.StartProcessRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class CamundaProcessService implements ProcessService {

  RestTemplate restTemplate;
  ObjectMapper mapper;

  @Override
  public <T> StartProcessResult startProcessById(String id, String businessKey, T data) {
    Map<String, String> params = new HashMap<>();
    params.put("id", id);
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/{id}/start",
      HttpMethod.POST,
      httpEntity(businessKey, data),
      StartProcessResult.class, params);
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  @Override
  public <T> StartProcessResult startProcessByKey(String key, String businessKey, T data) {
    Map<String, String> params = new HashMap<>();
    params.put("key", key);
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/key/{key}/start",
      HttpMethod.POST,
      httpEntity(businessKey, data),
      StartProcessResult.class, params);
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  @Override
  public <T> StartProcessResult startProcessByTenantId(String key, String tenantId, String businessKey, T data) {
    Map<String, String> params = new HashMap<>();
    params.put("key", key);
    params.put("tenant-id", tenantId);
    final ResponseEntity<StartProcessResult> response = restTemplate.exchange(
      "/process-definition/key/{key}/tenant-id/{tenant-id}/start",
      HttpMethod.POST,
      httpEntity(businessKey, data),
      StartProcessResult.class, params);
    return Optional.ofNullable(response.getBody()).orElseThrow();
  }

  private <T> StartProcessRequest buildRequest(String businessKey, T data) {
    return StartProcessRequest.builder()
      .businessKey(businessKey)
      .variables(PayloadUtil.extractPayload(mapper.convertValue(data, Map.class)))
      .build();
  }

  private <T> HttpEntity<StartProcessRequest> httpEntity(String businessKey, T data) {
    return new HttpEntity<>(buildRequest(businessKey, data));
  }

}
