package uz.keysoft.camunda.spring.boot.starter.service;

import uz.keysoft.camunda.spring.boot.starter.dto.VariablePair;
import uz.keysoft.camunda.spring.boot.starter.dto.task.CamundaTask;

import java.util.List;
import java.util.Map;

public interface TaskService {

  List<CamundaTask> getTaskListById(String processInstanceId);
  CamundaTask getTaskById(String taskId);
  void claim(String taskId, String userId);
  void unClaim(String taskId, String userId);
  <T> void complete(String taskId, T data);
  Map<String, VariablePair> getFormVariables(String taskId);


}
