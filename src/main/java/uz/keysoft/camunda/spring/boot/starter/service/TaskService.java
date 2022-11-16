package uz.keysoft.camunda.spring.boot.starter.service;

import uz.keysoft.camunda.spring.boot.starter.dto.task.CamundaTask;

import java.util.List;

public interface TaskService {

  List<CamundaTask> getTaskListById(String processInstanceId);
  void claim(String taskId, String userId);
  void unClaim(String taskId, String userId);
  <T> void complete(String taskId, T data);


}
