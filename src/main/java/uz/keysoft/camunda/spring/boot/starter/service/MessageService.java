package uz.keysoft.camunda.spring.boot.starter.service;

import java.util.Map;

public interface MessageService {

  <T> void correlateMessage(String messageName, String businessKey, T data);
  <T> void correlateMessage(String messageName, T data, Map<String, Object> keys);

}
