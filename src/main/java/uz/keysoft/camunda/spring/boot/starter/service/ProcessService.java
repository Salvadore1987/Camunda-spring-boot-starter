package uz.keysoft.camunda.spring.boot.starter.service;

import uz.keysoft.camunda.spring.boot.starter.dto.process.StartProcessResult;

import java.util.Map;

public interface ProcessService {

  <T> StartProcessResult startProcessById(String id, String businessKey, T data);
  <T> StartProcessResult startProcessByKey(String key, String businessKey, T data);
  <T> StartProcessResult startProcessByTenantId(String key, String tenantId, String businessKey, T data);
  StartProcessResult startProcessById(String id, String businessKey, Map<String, Object> variables);
  StartProcessResult startProcessByKey(String key, String businessKey, Map<String, Object> variables);
  StartProcessResult startProcessByTenantId(String key, String tenantId, String businessKey, Map<String, Object> variables);

}
