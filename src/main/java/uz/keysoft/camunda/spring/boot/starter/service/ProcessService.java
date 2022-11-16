package uz.keysoft.camunda.spring.boot.starter.service;

import uz.keysoft.camunda.spring.boot.starter.dto.process.StartProcessResult;

public interface ProcessService {

  <T> StartProcessResult startProcessById(String id, String businessKey, T data);
  <T> StartProcessResult startProcessByKey(String key, String businessKey, T data);
  <T> StartProcessResult startProcessByTenantId(String key, String tenantId, String businessKey, T data);

}
